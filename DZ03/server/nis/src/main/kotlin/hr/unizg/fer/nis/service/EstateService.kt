package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.Estate
import hr.unizg.fer.nis.model.EstateOwner
import hr.unizg.fer.nis.model.MAX_ESTATES
import hr.unizg.fer.nis.repository.EstateOwnerRepository
import hr.unizg.fer.nis.repository.EstateRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.Validator
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EstateService(
    private val estateRepository: EstateRepository,
    private val estateOwnerRepository: EstateOwnerRepository,
    private val validator: Validator
) {
    @Transactional
    fun createEstateForOwner(@Valid estate: Estate): Estate {
        val owner = estateOwnerRepository.findById(estate.estateOwner.id!!).get()
        owner.estates.add(estate)
        if (!isValidOwner(owner)) {
            throw IllegalArgumentException("Owner cannot have more than ${MAX_ESTATES} estates.")
        }
        estate.estateOwner = owner
        return estateRepository.save(estate)
    }

    fun getEstatesByOwnerId(ownerId: Long, pageable: Pageable) = estateRepository.findByEstateOwnerId(ownerId, pageable)

    fun getEstateById(id: Long): Estate = estateRepository.findById(id).orElseThrow { IllegalArgumentException("Estate with this ID does not exist.") }

    @Transactional
    fun deleteByOwnerIdAndEstateId(ownerId: Long, estateId: Long) = estateRepository.deleteByEstateOwnerIdAndId(ownerId, estateId)

    @Transactional
    fun updateEstate(@Valid estate: Estate): Estate {
        val existingEstate = estateRepository.findById(estate.id!!)
        if (existingEstate.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing estate.")
        }
        val owner = estateOwnerRepository.findById(estate.estateOwner.id!!).get()
        owner.estates.add(estate)
        if (!isValidOwner(owner)) {
            throw IllegalArgumentException("Owner cannot have more than ${MAX_ESTATES} estates.")
        }
        estate.estateOwner = owner
        return estateRepository.save(estate)
    }

    private fun isValidOwner(owner: EstateOwner): Boolean {
        val violations = validator.validate(owner)
        return violations.isEmpty()
    }
}