package hr.unizg.fer.nis.controller

import java.time.Instant

data class EstateOwnerCreateRequest(
    val name: String,
    val surname: String,
    val birthDate: Instant?,
    val address: String,
    val email: String,
    val townId: Long
)

data class EstateOwnerUpdateRequest(
    val id: Long,
    val name: String,
    val surname: String,
    val birthDate: Instant?,
    val address: String,
    val email: String,
    val townId: Long
)