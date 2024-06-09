package hr.unizg.fer.nis.ports.usecases.requests

data class CamundaStartProcessRequest(
    val requestId: Long,
    val buyerId: String,
    val estateId: Long
)