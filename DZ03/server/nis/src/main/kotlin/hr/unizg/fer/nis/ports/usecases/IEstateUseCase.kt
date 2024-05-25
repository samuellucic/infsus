package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateUpdateRequest
import hr.unizg.fer.nis.ports.usecases.results.EstateResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IEstateUseCase {
    fun createEstateForOwner(estateCreateRequest: EstateCreateRequest): EstateResult
    fun getEstatesByOwnerId(ownerId: Long, pageable: Pageable): Page<EstateResult>
    fun getEstateById(id: Long): EstateResult
    fun deleteByOwnerIdAndEstateId(ownerId: Long, estateId: Long)
    fun updateEstate(estateUpdateRequest: EstateUpdateRequest): EstateResult
}
