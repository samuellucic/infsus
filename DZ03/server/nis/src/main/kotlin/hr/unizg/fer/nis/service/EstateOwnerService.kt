package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.EstateOwner
import hr.unizg.fer.nis.repository.EstateOwnerRepository
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EstateOwnerService(
    private val estateOwnerRepository: EstateOwnerRepository,
    private val townService: TownService
) {
    fun createEstateOwner(@Valid estateOwner: EstateOwner, townId: Long): EstateOwner {
        estateOwner.town = townService.getTownById(townId)
        return estateOwnerRepository.save(estateOwner)
    }

    fun getEstateOwnerById(id: Long) = estateOwnerRepository.findById(id).orElseThrow { IllegalArgumentException("EstateOwner with this ID does not exist.") }

    fun deleteEstateOwnerById(id: Long) = estateOwnerRepository.deleteById(id)

    @Transactional
    fun updateEstateOwner(@Valid estateOwner: EstateOwner): EstateOwner {
        val existingEstateOwner = estateOwnerRepository.findById(estateOwner.id!!)
        if (existingEstateOwner.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing EstateOwner.")
        }
        return estateOwnerRepository.save(estateOwner)
    }

    fun getAllOwnersPaginated(pageable: Pageable) = estateOwnerRepository.findAll(pageable)
}
