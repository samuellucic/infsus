package hr.unizg.fer.nis.ports.usecases.requests


data class CamundaSuggestTourRequest(
    val taskId: String,
    val tourDateTime: String
)