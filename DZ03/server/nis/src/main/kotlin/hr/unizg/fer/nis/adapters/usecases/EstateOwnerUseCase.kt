package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.ports.usecases.IEstateOwnerUseCase
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequestMapper
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequestMapper
import hr.unizg.fer.nis.ports.usecases.results.EstateOwnerResult
import hr.unizg.fer.nis.ports.usecases.results.mapToEstateOwnerResult
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EstateOwnerUseCase(
    private val estateOwnerRepository: IEstateOwnerRepository,
    private val estateOwnerCreateRequestMapper: EstateOwnerCreateRequestMapper,
    private val estateOwnerUpdateRequestMapper: EstateOwnerUpdateRequestMapper
): IEstateOwnerUseCase {
    override fun createEstateOwner(estateOwnerCreateRequest: EstateOwnerCreateRequest): EstateOwnerResult {
        val estateOwner = estateOwnerCreateRequestMapper.mapToEstateOwner(estateOwnerCreateRequest)
        return estateOwnerRepository.save(estateOwner).mapToEstateOwnerResult()
    }

    override fun getEstateOwnerById(id: Long) = estateOwnerRepository
        .findById(id)
        .map { it.mapToEstateOwnerResult() }
        .orElseThrow { IllegalArgumentException("EstateOwner with this ID does not exist.") }

    override fun deleteEstateOwnerById(id: Long) = estateOwnerRepository.deleteById(id)

    @Transactional
    override fun updateEstateOwner(estateOwnerUpdateRequest: EstateOwnerUpdateRequest): EstateOwnerResult {
        val estateOwner = estateOwnerUpdateRequestMapper.mapToEstateOwner(estateOwnerUpdateRequest)
        val existingEstateOwner = estateOwnerRepository.findById(estateOwner.id!!)
        if (existingEstateOwner.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing EstateOwner.")
        }
        return estateOwnerRepository.save(estateOwner).mapToEstateOwnerResult()
    }

    override fun getAllOwnersPaginated(pageable: Pageable) = estateOwnerRepository.findAll(pageable).map { it.mapToEstateOwnerResult() }
}
