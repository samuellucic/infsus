package hr.unizg.fer.nis.ports.usecases.results

import hr.unizg.fer.nis.domain.models.Estate
import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.domain.models.Town

data class EstateResult(
    val id: Long,
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    var estateOwnerId: Long,
    var town: Town,
    var estateType: EstateType
)

fun Estate.mapToEstateResult() = EstateResult(
    id = id!!,
    price = price,
    address = address,
    area = area,
    description = description,
    estateOwnerId = estateOwner.id!!,
    town = town,
    estateType = estateType
)