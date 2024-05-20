package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.Town
import hr.unizg.fer.nis.repository.TownRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TownService(
    private val townRepository: TownRepository
) {
    fun createTown(town: Town) = townRepository.save(town)

    fun getTownById(id:Long) = townRepository.findById(id).orElseThrow { IllegalArgumentException("Town with this ID does not exist.") }

    fun deleteTownById(id: Long) = townRepository.deleteById(id)

    @Transactional
    fun updateTown(town: Town): Town {
        val existingTown = townRepository.findById(town.id!!)
        if(existingTown.isEmpty) {
            throw IllegalArgumentException("Cannot update non existing town.")
        }
        return townRepository.save(town)
    }

    fun getAllTownsPaginated(pageable: Pageable) = townRepository.findAll(pageable)
}