package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.ports.usecases.ICamundaUseCase
import hr.unizg.fer.nis.ports.usecases.requests.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/camunda")
class CamundaController(
    private val camundaUseCase: ICamundaUseCase
) {

    @PostMapping("/start")
    fun startCamundaProcess(@RequestBody camundaStartProcessRequest: CamundaStartProcessRequest) = camundaUseCase.startCamundaProcess(camundaStartProcessRequest)

    @PostMapping("/presuggest")
    fun preSuggestTour(@RequestBody camundaPreSuggestTourRequest: CamundaPreSuggestTourRequest) = camundaUseCase.preSuggestTour(camundaPreSuggestTourRequest)


//    @PostMapping("/check")
//    fun isUserInGroup(@RequestBody camundaCheckUserGroupRequest: CamundaCheckUserGroupRequest) = camundaUseCase.isUserInGroup(camundaCheckUserGroupRequest)

    @PostMapping("/pick")
    fun pickTask(@RequestBody pickTaskRequest: CamundaPickTaskRequest) = camundaUseCase.pickTask(pickTaskRequest)

    @PostMapping("/finish")
    fun finishTask(@RequestBody taskId: String) = camundaUseCase.finishTask(taskId)

    @PostMapping("/suggest")
    fun suggestTour(@RequestBody camundaSuggestTourRequest: CamundaSuggestTourRequest) = camundaUseCase.suggestTour(camundaSuggestTourRequest)

    @GetMapping("/tasks")
    fun getTasks(@RequestParam("userId") userId: String) = camundaUseCase.getTasks(userId)

    @PostMapping("/decide")
    fun decideSuggestedTour(@RequestBody camundaDecideRequest: CamundaDecideRequest) = camundaUseCase.decideSuggestedTour(camundaDecideRequest)

    @GetMapping("/unassigned")
    fun getUnassignedTasksPerGroup(@RequestParam("groupId") groupId: String) = camundaUseCase.getUnassignedTasksPerGroup(groupId)

    @GetMapping("/tourinfo")
    fun getTourInfo(@RequestParam pid: String) = camundaUseCase.getTourInfo(pid)
}