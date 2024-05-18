package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.Town
import hr.unizg.fer.nis.repository.TownRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TownService(
    private val townRepository: TownRepository
) {
    fun createTown(town: Town) = townRepository.save(town)

    fun getTownById(id:Long) = townRepository.getReferenceById(id)

    fun deleteTownById(id: Long) = townRepository.deleteById(id)

    @Transactional
    fun updateTown(town: Town) {
        val existingTown = townRepository.findById(town.id!!)
        if(existingTown.isEmpty) {
            throw IllegalArgumentException("Cannot update non existing town.")
        }
        townRepository.save(town)
    }
}