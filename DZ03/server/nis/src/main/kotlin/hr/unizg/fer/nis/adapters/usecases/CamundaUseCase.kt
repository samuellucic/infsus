package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.Notification
import hr.unizg.fer.nis.ports.repositories.IEstateRepository
import hr.unizg.fer.nis.ports.repositories.INotificationRepository
import hr.unizg.fer.nis.ports.usecases.ICamundaUseCase
import hr.unizg.fer.nis.ports.usecases.requests.*
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.identity.User
import org.camunda.bpm.engine.impl.IdentityServiceImpl
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity
import org.camunda.bpm.engine.variable.Variables
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.stream.Collectors

@Service
class CamundaUseCase(
    private val runtimeService: RuntimeService,
    private val taskService: TaskService,
    private val historyService: HistoryService,
    private val estateRepository: IEstateRepository,
    private val notificationRepository: INotificationRepository
): ICamundaUseCase {

    private val identityService: IdentityService = IdentityServiceImpl()

    override fun startCamundaProcess(camundaStartProcessRequest: CamundaStartProcessRequest): String {
        val variables = Variables.createVariables()
            .putValue("requestId", camundaStartProcessRequest.requestId)
            .putValue("buyerId", camundaStartProcessRequest.buyerId)
            .putValue("estateId", camundaStartProcessRequest.estateId)

        return runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables).processInstanceId
    }

    override fun preSuggestTour(camundaPreSuggestTourRequest: CamundaPreSuggestTourRequest) {
        val variables = mapOf(
            "agentId" to camundaPreSuggestTourRequest.agentId
        )
        runtimeService.createMessageCorrelation(SUGGEST_TOUR_MESSAGE)
            .processInstanceId(camundaPreSuggestTourRequest.pid)
            .setVariables(variables)
            .correlate()
    }

//    override fun isUserInGroup(camundaCheckUserGroupRequest: CamundaCheckUserGroupRequest) =
//        identityService.createUserQuery().userId(camundaCheckUserGroupRequest.userId).memberOfGroup(camundaCheckUserGroupRequest.groupId).singleResult() != null

    override fun pickTask(pickTaskRequest: CamundaPickTaskRequest) =
        taskService.claim(pickTaskRequest.taskId, pickTaskRequest.userId)

    override fun finishTask(taskId: String) =
        taskService.complete(taskId)

    override fun suggestTour(camundaSuggestTourRequest: CamundaSuggestTourRequest) {
        val variables = mapOf(
            "TourDateTime" to camundaSuggestTourRequest.tourDateTime
        )

        taskService.complete(camundaSuggestTourRequest.taskId, variables)
    }

    override fun getTasks(userId: String): List<TaskInfo> {
        val userTasks = taskService.createTaskQuery()
            .taskAssignee(userId)
            .processDefinitionKey(PROCESS_KEY)
            .orderByTaskCreateTime()
            .asc()
            .list()

        return userTasks.stream()
            .map { task ->
                val taskInfo = TaskInfo(
                    tId = task.id,
                    taskName = task.name,
                    taskKey = task.taskDefinitionKey,
                    pId = task.processInstanceId,
                    startTime = task.createTime,
                )
                loadTaskVariables(taskInfo)
                taskInfo
            }
            .collect(Collectors.toList())
    }

    override fun decideSuggestedTour(camundaDecideRequest: CamundaDecideRequest) {
        val variables = mapOf(
            "TermFits" to camundaDecideRequest.termFits
        )

        taskService.complete(camundaDecideRequest.taskId, variables)
    }

    override fun getTourInfo(): List<TourInfo> {
        val historyList = historyService.createHistoricProcessInstanceQuery()
            .processDefinitionKey(PROCESS_KEY)
            .list()

        return historyList
            .map {
                val tourInfo = getTourInfo(it.id)

                tourInfo.startTime = it.startTime
                tourInfo.ended = it.endTime != null
                loadUserData(tourInfo)
                tourInfo
            }
            .filter {
                !it.ended!!
            }
    }

    override fun getTourInfo(pid: String): TourInfo {
        val restTemplate = RestTemplate()
        val getUrl = "http://localhost:8080/engine-rest/history/variable-instance?processInstanceId=$pid"
        val response: ResponseEntity<Array<VariableInstance>> =
            restTemplate.getForEntity(getUrl, Array<VariableInstance>::class.java)
        val variables = response.body?.toList() ?: emptyList()

        val pid = variables.first().processInstanceId
        return TourInfo(
            requestId = variables.find { it.name == "requestId" }?.value as Int?,
            termFits = variables.find { it.name == "termFits" }?.value as Boolean?,
            tourDateTime = variables.find { it.name == "TourDateTime" }?.value as String?,
            agentId = variables.find { it.name == "agentId" }?.value as String?,
            buyerId = variables.find { it.name == "buyerId" }?.value as String?,
            estateId = variables.find { it.name == "estateId" }?.value as Int?,
            pid = pid
        )
    }

    override fun getUnassignedTasksPerGroup(groupId: String): List<TaskInfo> {
        val groupTasks = taskService.createTaskQuery()
            .taskCandidateGroup(groupId)
            .processDefinitionKey(PROCESS_KEY)
            .orderByTaskCreateTime()
            .asc()
            .list()

        return groupTasks.stream()
            .map { task ->
                val taskInfo = TaskInfo(
                    tId = task.id,
                    taskName = task.name,
                    taskKey = task.taskDefinitionKey,
                    pId = task.processInstanceId,
                    startTime = task.createTime
                )
                loadTaskVariables(taskInfo)
                taskInfo
            }
            .collect(Collectors.toList())
    }

    override fun getNotificationsForUser(userId: String): List<Notification> {
        val notifications = notificationRepository.findByUserIdAndDelivered(userId, 0)
        notifications.forEach {
            val readNotification = it.copy(delivered = 1)
            notificationRepository.save(readNotification)
        }

        return notifications
    }

    private fun loadTaskVariables(taskInfo: TaskInfo) {
        val variables = taskService.getVariables(taskInfo.tId)
        taskInfo.variables = variables
        loadUserData(taskInfo)
    }

    private fun loadUserData(taskInfo: TaskInfo) {
        val userData = loadUserData((taskInfo.variables!!["estateId"] as Long).toInt(), taskInfo.variables!!["buyerId"] as String, taskInfo.variables!!["agentId"] as String)

        userData.buyerName?.let {
            taskInfo.variables!!["buyerName"] = userData.buyerName!!
        }
        userData.agentName?.let {
            taskInfo.variables!!["agentName"] = userData.agentName!!
        }
        taskInfo.variables!!["estateDescription"] = userData.estateDescription!!
    }

    private fun loadUserData(tourInfo: TourInfo) {
        val userData = loadUserData(tourInfo.estateId!!, tourInfo.buyerId, tourInfo.agentId)
        tourInfo.buyerName = userData.buyerName
        tourInfo.agentName = userData.agentName
        tourInfo.estateDescription = userData.estateDescription
    }

    private fun loadUserData(estateId: Int, buyerId: String? = null, agentId: String? = null): UserData {
        val restTemplate = RestTemplate()

        val buyerUrl = "http://localhost:8080/engine-rest/user/${buyerId}/profile"
        val agentUrl = "http://localhost:8080/engine-rest/user/${agentId}/profile"

        val userData = UserData()

        buyerId?.let {
            val buyerInfo: User? = restTemplate.getForEntity(buyerUrl, UserEntity::class.java).body
            userData.buyerName = "${buyerInfo?.firstName} ${buyerInfo?.lastName}"
        }
        agentId?.let {
            val agentInfo: User? = restTemplate.getForEntity(agentUrl, UserEntity::class.java).body
            userData.agentName = "${agentInfo?.firstName} ${agentInfo?.lastName}"
        }
        userData.estateDescription = estateRepository.findById(estateId.toLong()).get().description


        return userData
    }

    private data class UserData (
        var buyerName: String? = null,
        var agentName: String? = null,
        var estateDescription: String? = null,
    )

    private companion object {
        const val PROCESS_KEY = "TourArrangement"
        const val SUGGEST_TOUR_MESSAGE = "RequestConfirmation"
    }
}
