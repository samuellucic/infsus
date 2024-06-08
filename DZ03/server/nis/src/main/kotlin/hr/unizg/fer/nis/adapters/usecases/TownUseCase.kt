package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.ITownRepository
import hr.unizg.fer.nis.ports.usecases.ITownUseCase
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TownUseCase(
    private val ITownRepository: ITownRepository
): ITownUseCase {
    override fun createTown(@Valid town: Town) = ITownRepository.save(town)

    override fun getTownById(id:Long) = ITownRepository.findById(id).orElseThrow { IllegalArgumentException("Town with this ID does not exist.") }

    override fun deleteTownById(id: Long) = ITownRepository.deleteById(id)

    @Transactional
    override fun updateTown(@Valid town: Town): Town {
        val existingTown = ITownRepository.findById(town.id!!)
        if(existingTown.isEmpty) {
            throw IllegalArgumentException("Cannot update non existing town.")
        }
        return ITownRepository.save(town)
    }

    override fun getAllTownsPaginated(pageable: Pageable) = ITownRepository.findAll(pageable)
}
