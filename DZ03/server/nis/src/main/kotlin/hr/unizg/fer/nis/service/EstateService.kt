package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.Estate
import hr.unizg.fer.nis.repository.EstateRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EstateService(
    private val estateRepository: EstateRepository
) {
    @Transactional
    fun createEstateForOwner(estate: Estate): Estate = estateRepository.save(estate)

    fun getEstatesByOwnerId(ownerId: Long, pageable: Pageable) = estateRepository.findByEstateOwnerId(ownerId, pageable)

    fun getEstateById(id: Long): Estate = estateRepository.findById(id).orElseThrow { IllegalArgumentException("Estate with this ID does not exist.") }

    @Transactional
    fun deleteByOwnerIdAndEstateId(ownerId: Long, estateId: Long) = estateRepository.deleteByEstateOwnerIdAndId(ownerId, estateId)

    @Transactional
    fun updateEstate(estate: Estate): Estate {
        val existingEstate = estateRepository.findById(estate.id!!)
        if (existingEstate.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing estate.")
        }
        return estateRepository.save(estate)
    }
}