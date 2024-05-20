package hr.unizg.fer.nis.service

import hr.unizg.fer.nis.model.EstateType
import hr.unizg.fer.nis.repository.EstateTypeRepository
import org.springframework.stereotype.Service

@Service
class EstateTypeService(
    private val estateTypeRepository: EstateTypeRepository
) {

    fun createEstateType(estateType: EstateType) = estateTypeRepository.save(estateType)

    fun getEstateTypeByName(name: String) = estateTypeRepository.findById(name).orElseThrow { IllegalArgumentException("Estate type with this name does not exist") }

    fun updateEstateType(estateType: EstateType) {
        val existingEstateType = estateTypeRepository.findById(estateType.name)
        if(existingEstateType.isEmpty)
            throw IllegalArgumentException("Estate type with this name does not exist")
        estateTypeRepository.save(estateType)
    }

    fun deleteEstateTypeByName(name: String) = estateTypeRepository.deleteById(name)
}