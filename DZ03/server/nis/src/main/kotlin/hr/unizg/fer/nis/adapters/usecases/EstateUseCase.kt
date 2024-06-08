package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.MAX_ESTATES
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import hr.unizg.fer.nis.ports.repositories.IEstateRepository
import hr.unizg.fer.nis.ports.usecases.IEstateUseCase
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateMapper
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateUpdateMapper
import hr.unizg.fer.nis.ports.usecases.requests.EstateUpdateRequest
import hr.unizg.fer.nis.ports.usecases.results.EstateResult
import hr.unizg.fer.nis.ports.usecases.results.mapToEstateResult
import jakarta.transaction.Transactional
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class EstateUseCase(
    private val IEstateRepository: IEstateRepository,
    private val IEstateOwnerRepository: IEstateOwnerRepository,
    private val validator: Validator,
    private val estateCreateMapper: EstateCreateMapper,
    private val estateUpdateMapper: EstateUpdateMapper
): IEstateUseCase {

    override fun getEstates() = IEstateRepository.findAll().map { it.mapToEstateResult() }

    @Transactional
    override fun createEstateForOwner(estateCreateRequest: EstateCreateRequest): EstateResult {
        val estate = estateCreateMapper.mapToEstate(estateCreateRequest)

        val violations = validator.validate(estate)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }

        val owner = IEstateOwnerRepository.findById(estate.estateOwner.id!!).getOrElse { throw IllegalArgumentException("Owner with this ID does not exist.") }
        owner.estates.add(estate)
        if (!isValidOwner(owner)) {
            throw IllegalArgumentException("Owner cannot have more than $MAX_ESTATES estates.")
        }

        estate.estateOwner = owner
        return IEstateRepository.save(estate).mapToEstateResult()
    }

    override fun getEstatesByOwnerId(ownerId: Long, pageable: Pageable) =
        IEstateRepository.findByEstateOwnerId(ownerId, pageable).map { it.mapToEstateResult() }

    override fun getEstateById(id: Long): EstateResult =
        IEstateRepository
            .findById(id)
            .map { it.mapToEstateResult() }
            .orElseThrow { IllegalArgumentException("Estate with this ID does not exist.") }

    @Transactional
    override fun deleteByOwnerIdAndEstateId(ownerId: Long, estateId: Long) = IEstateRepository.deleteByEstateOwnerIdAndId(ownerId, estateId)

    @Transactional
    override fun updateEstate(estateUpdateRequest: EstateUpdateRequest): EstateResult {
        val estate = estateUpdateMapper.mapToEstate(estateUpdateRequest)
        val violations = validator.validate(estate)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }

        val existingEstate = IEstateRepository.findById(estate.id!!)
        if (existingEstate.isEmpty) {
            throw IllegalArgumentException("Cannot update non-existing estate.")
        }
        val owner = IEstateOwnerRepository.findById(estate.estateOwner.id!!).getOrElse { throw IllegalArgumentException("Owner with this ID does not exist.") }
        if(owner.id != estate.estateOwner.id) {
            owner.estates.add(estate)
        }
        if (!isValidOwner(owner)) {
            throw IllegalArgumentException("Owner cannot have more than $MAX_ESTATES estates.")
        }
        estate.estateOwner = owner
        return IEstateRepository.save(estate).mapToEstateResult()
    }

    private fun isValidOwner(owner: EstateOwner): Boolean {
        val violations = validator.validate(owner)
        return violations.isEmpty()
    }
}
