package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.ports.usecases.requests.*

interface ICamundaUseCase {
    fun startCamundaProcess(camundaStartProcessRequest: CamundaStartProcessRequest): String

    fun preSuggestTour(camundaPreSuggestTourRequest: CamundaPreSuggestTourRequest)

//    fun isUserInGroup(camundaCheckUserGroupRequest: CamundaCheckUserGroupRequest): Boolean

    fun pickTask(pickTaskRequest: CamundaPickTaskRequest)

    fun finishTask(taskId: String)

    fun suggestTour(camundaSuggestTourRequest: CamundaSuggestTourRequest)

    fun getTasks(userId: String): List<TaskInfo>

    fun decideSuggestedTour(camundaDecideRequest: CamundaDecideRequest)

    fun getTourInfo(pid: String): TourInfo

    fun getUnassignedTasksPerGroup(groupId: String): List<TaskInfo>
}