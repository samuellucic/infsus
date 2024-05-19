package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.Estate
import hr.unizg.fer.nis.repository.EstateOwnerRepository
import hr.unizg.fer.nis.repository.EstateRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class EstateService(
    private val estateRepository: EstateRepository,
    private val estateOwnerService: EstateOwnerService
) {
    @Transactional
    fun createEstateForOwner(ownerId: Long, estate: Estate): Estate {
        val owner = estateOwnerService.getEstateOwnerById(ownerId)
        estate.estateOwner = owner
        return estateRepository.save(estate)
    }

    fun getEstatesByOwnerId(ownerId: Long): List<Estate> = estateRepository.findByEstateOwnerId(ownerId)

    fun getEstateById(id: Long): Estate = estateRepository.findById(id).orElseThrow { IllegalArgumentException("Estate with this ID does not exist.") }

    fun deleteEstateById(id: Long) = estateRepository.deleteById(id)

    @Transactional
    fun updateEstate(estate: Estate): Estate {
        val existingEstate = estateRepository.findById(estate.id!!)
        if (existingEstate.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing estate.")
        }
        return estateRepository.save(estate)
    }
}