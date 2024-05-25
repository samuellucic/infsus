package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.domain.models.Town
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ITownUseCase {
    fun createTown(@Valid town: Town): Town
    fun getTownById(id:Long): Town
    fun deleteTownById(id: Long)
    fun updateTown(@Valid town: Town): Town
    fun getAllTownsPaginated(pageable: Pageable): Page<Town>
}