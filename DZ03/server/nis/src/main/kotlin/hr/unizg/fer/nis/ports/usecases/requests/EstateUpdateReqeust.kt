package hr.unizg.fer.nis.ports.usecases.requests

import hr.unizg.fer.nis.domain.models.Estate
import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.adapters.usecases.EstateTypeUseCase
import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrElse

data class EstateUpdateRequest(
    val id: Long,
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    val ownerId: Long,
    val townId: Long,
    val estateTypeName: String
)

@Component
class EstateUpdateMapper @Autowired constructor(
    private val townUseCase: TownUseCase,
    private val estateTypeUseCase: EstateTypeUseCase,
    private val estateOwnerRepository: IEstateOwnerRepository
) {
    fun mapToEstate(estateUpdateRequest: EstateUpdateRequest) = Estate(
        id = estateUpdateRequest.id,
        price = estateUpdateRequest.price,
        address = estateUpdateRequest.address,
        area = estateUpdateRequest.area,
        description = estateUpdateRequest.description,
        estateOwner = estateOwnerRepository.findById(estateUpdateRequest.ownerId).getOrElse { throw IllegalArgumentException("Estate owner with this ID does not exist.") },
        town = townUseCase.getTownById(estateUpdateRequest.townId) as Town,
        estateType = estateTypeUseCase.getEstateTypeByName(estateUpdateRequest.estateTypeName) as EstateType
    )
}