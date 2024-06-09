package hr.unizg.fer.nis.ports.usecases.requests

import hr.unizg.fer.nis.adapters.usecases.EstateTypeUseCase
import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import hr.unizg.fer.nis.domain.models.Estate
import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrElse

data class EstateCreateRequest(
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    val ownerId: Long,
    val townId: Long,
    val estateTypeName: String
)

@Component
class EstateCreateMapper @Autowired constructor(
    private val townUseCase: TownUseCase,
    private val estateTypeUseCase: EstateTypeUseCase,
    private val estateOwnerRepository: IEstateOwnerRepository
) {
    fun mapToEstate(estateCreateRequest: EstateCreateRequest) = Estate(
        price = estateCreateRequest.price,
        address = estateCreateRequest.address,
        area = estateCreateRequest.area,
        description = estateCreateRequest.description,
        estateOwner = estateOwnerRepository.findById(estateCreateRequest.ownerId).getOrElse { throw IllegalArgumentException("Estate owner with this ID does not exist.") },
        town = townUseCase.getTownById(estateCreateRequest.townId) as Town,
        estateType = estateTypeUseCase.getEstateTypeByName(estateCreateRequest.estateTypeName) as EstateType
    )
}
