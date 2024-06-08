package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.ports.usecases.requests.CamundaDecideRequest
import hr.unizg.fer.nis.ports.usecases.requests.CamundaPickTaskRequest
import hr.unizg.fer.nis.ports.usecases.requests.CamundaPreSuggestTourRequest
import hr.unizg.fer.nis.ports.usecases.requests.CamundaStartProcessRequest
import hr.unizg.fer.nis.ports.usecases.requests.CamundaSuggestTourRequest
import hr.unizg.fer.nis.ports.usecases.requests.TaskInfo
import hr.unizg.fer.nis.ports.usecases.requests.TourInfo

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

    fun getTourInfo(): List<TourInfo>

    fun getUnassignedTasksPerGroup(groupId: String): List<TaskInfo>
}
