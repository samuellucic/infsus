package hr.unizg.fer.nis.ports.usecases.requests

import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

data class EstateOwnerUpdateRequest(
    val id: Long,
    val name: String,
    val surname: String,
    val birthDate: Instant?,
    val address: String,
    val email: String,
    val townId: Long
)

@Component
class EstateOwnerUpdateRequestMapper @Autowired constructor(
    private val townUseCase: TownUseCase
) {
    fun mapToEstateOwner(estateOwnerUpdateRequest: EstateOwnerUpdateRequest) = EstateOwner(
        id = estateOwnerUpdateRequest.id,
        name = estateOwnerUpdateRequest.name,
        surname = estateOwnerUpdateRequest.surname,
        birthDate = estateOwnerUpdateRequest.birthDate,
        address = estateOwnerUpdateRequest.address,
        email = estateOwnerUpdateRequest.email,
        town = townUseCase.getTownById(estateOwnerUpdateRequest.townId) as Town
    )
}