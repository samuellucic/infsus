package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.ports.repositories.IEstateTypeRepository
import hr.unizg.fer.nis.ports.usecases.IEstateTypeUseCase
import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EstateTypeUseCase(
    private val IEstateTypeRepository: IEstateTypeRepository
): IEstateTypeUseCase {

    override fun createEstateType(@Valid estateType: EstateType): EstateType {
        if (IEstateTypeRepository.findById(estateType.name).isPresent) {
            throw DataIntegrityViolationException("Estate type with this name already exists.")
        }
        return IEstateTypeRepository.save(estateType)
    }

    override fun getEstateTypeByName(name: String) = IEstateTypeRepository.findById(name).orElseThrow { IllegalArgumentException("Estate type with this name does not exist.") }

    override fun updateEstateType(estateType: EstateType): EstateType {
        val existingEstateType = IEstateTypeRepository.findById(estateType.name)
        if(existingEstateType.isEmpty)
            throw IllegalArgumentException("Estate type with this name does not exist")
        return IEstateTypeRepository.save(estateType)
    }

    override fun deleteEstateTypeByName(name: String) = IEstateTypeRepository.deleteById(name)

    override fun getAllEstateTypes(pageable: Pageable) = IEstateTypeRepository.findAll(pageable)
}
