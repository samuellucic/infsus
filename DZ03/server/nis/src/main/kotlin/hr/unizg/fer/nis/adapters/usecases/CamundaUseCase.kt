package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.ports.usecases.ICamundaUseCase
import hr.unizg.fer.nis.ports.usecases.requests.*
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.task.Task
import org.camunda.bpm.engine.variable.Variables
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CamundaUseCase(
    private val runtimeService: RuntimeService,
    //private val identityService: IdentityService,
    private val taskService: TaskService,
    private val historyService: HistoryService
): ICamundaUseCase {

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
                TaskInfo(
                    tId = task.id,
                    taskName = task.name,
                    taskKey = task.taskDefinitionKey,
                    pId = task.processInstanceId,
                    startTime = task.createTime
                )
            }
            .collect(Collectors.toList())
    }

    override fun decideSuggestedTour(camundaDecideRequest: CamundaDecideRequest) {
        val variables = mapOf(
            "TermFits" to camundaDecideRequest.termFits
        )

        taskService.complete(camundaDecideRequest.taskId, variables)
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

    private fun loadTaskVariables(taskInfo: TaskInfo) {
        val variables = taskService.getVariables(taskInfo.tId)
        taskInfo.variables = variables
    }

//    override fun getTourInfo(): List<TourInfo> {
//        val historyList = historyService.createHistoricProcessInstanceQuery()
//            .processDefinitionKey(PROCESS_KEY)
//            .list()
//        print(historyList[0])
//        val tours = historyList.map {
//            val tourInfo = TourInfo(
//                pid = it.id,
//                startTime = it.startTime,
//                endTime = it.endTime,
//                ended = it.endTime != null
//            )
//            loadInstanceVariables(tourInfo)
//            tourInfo
//        }
//
//        return tours
//    }
//
//
//    private fun loadInstanceVariables(tourInfo: TourInfo) {
//        print("eee")
//        print(tourInfo)
//        val variablesList = historyService.createHistoricVariableInstanceQuery()
//            .processInstanceId(tourInfo.pid)
//            .list()
//
//        tourInfo.agentId = variablesList.find { it.variableName == "agentId" }?.value as String?
//        tourInfo.termFits = variablesList.find { it.variableName == "termFits" }?.value as Boolean?
//        tourInfo.tourDateTime = variablesList.find { it.variableName == "tourDateTime" }?.value as String?
//        tourInfo.requestId = variablesList.find { it.variableName == "requestId" }?.value as String?
//        tourInfo.buyerId = variablesList.find { it.variableName == "buyerId" }?.value as String?
//        tourInfo.estateId = variablesList.find { it.variableName == "estateId" }?.value as String?
//    }


    private companion object {
        const val PROCESS_KEY = "TourArrangement"
        const val SUGGEST_TOUR_MESSAGE = "RequestConfirmation"
    }
}