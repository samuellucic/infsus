package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.EstateType
import hr.unizg.fer.nis.repository.EstateTypeRepository
import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EstateTypeService(
    private val estateTypeRepository: EstateTypeRepository
) {

    fun createEstateType(@Valid estateType: EstateType): EstateType {
        if (estateTypeRepository.findById(estateType.name).isPresent) {
            throw DataIntegrityViolationException("Estate type with this name already exists.")
        }
        return estateTypeRepository.save(estateType)
    }

    fun getEstateTypeByName(name: String) = estateTypeRepository.findById(name).orElseThrow { IllegalArgumentException("Estate type with this name does not exist.") }

    fun updateEstateType(estateType: EstateType): EstateType {
        val existingEstateType = estateTypeRepository.findById(estateType.name)
        if(existingEstateType.isEmpty)
            throw IllegalArgumentException("Estate type with this name does not exist")
        return estateTypeRepository.save(estateType)
    }

    fun deleteEstateTypeByName(name: String) = estateTypeRepository.deleteById(name)

    fun getAllEstateTypes(pageable: Pageable) = estateTypeRepository.findAll(pageable)
}