package hr.unizg.fer.nis.ports.usecases

import hr.unizg.fer.nis.domain.models.EstateType
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IEstateTypeUseCase {
    fun createEstateType(@Valid estateType: EstateType): EstateType
    fun getEstateTypeByName(name: String): EstateType
    fun updateEstateType(estateType: EstateType): EstateType
    fun deleteEstateTypeByName(name: String)
    fun getAllEstateTypes(pageable: Pageable): Page<EstateType>
}