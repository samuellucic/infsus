package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequest
import hr.unizg.fer.nis.ports.usecases.results.EstateOwnerResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IEstateOwnerUseCase {
    fun createEstateOwner(estateOwnerCreateRequest: EstateOwnerCreateRequest): EstateOwnerResult
    fun getEstateOwnerById(id: Long): EstateOwnerResult
    fun deleteEstateOwnerById(id: Long)
    fun updateEstateOwner(estateOwnerUpdateRequest: EstateOwnerUpdateRequest): EstateOwnerResult
    fun getAllOwnersPaginated(pageable: Pageable): Page<EstateOwnerResult>
}