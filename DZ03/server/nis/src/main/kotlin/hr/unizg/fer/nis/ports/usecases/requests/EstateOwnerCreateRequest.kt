package hr.unizg.fer.nis.ports.usecases.requests

import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.Town
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

data class EstateOwnerCreateRequest(
    val name: String,
    val surname: String,
    val birthDate: Instant?,
    val address: String,
    val email: String,
    val townId: Long
)

@Component
class EstateOwnerCreateRequestMapper @Autowired constructor(
    private val townUseCase: TownUseCase
) {
    fun mapToEstateOwner(estateOwnerCreateRequest: EstateOwnerCreateRequest) = EstateOwner(
        name = estateOwnerCreateRequest.name,
        surname = estateOwnerCreateRequest.surname,
        birthDate = estateOwnerCreateRequest.birthDate,
        address = estateOwnerCreateRequest.address,
        email = estateOwnerCreateRequest.email,
        town = townUseCase.getTownById(estateOwnerCreateRequest.townId) as Town
    )
}
