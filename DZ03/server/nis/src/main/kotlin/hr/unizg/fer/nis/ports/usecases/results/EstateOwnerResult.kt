package hr.unizg.fer.nis.ports.usecases.results

import hr.unizg.fer.nis.domain.models.EstateOwner
import java.time.Instant

data class EstateOwnerResult(
    val id: Long,
    val name: String,
    val surname: String,
    val birthDate: Instant?,
    val address: String,
    val email: String,
    val townId: Long
)

fun EstateOwner.mapToEstateOwnerResult() = EstateOwnerResult(
    id = id!!,
    name = name,
    surname = surname,
    birthDate = birthDate,
    address = address,
    email = email,
    townId = town.id!!
)