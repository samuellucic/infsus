package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.adapters.usecases.EstateTypeUseCase
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/estateType")
class EstateTypeController(
    private val estateTypeUseCase: EstateTypeUseCase
) {

    @PostMapping
    fun createEstateType(@RequestBody estateType: EstateType) = estateTypeUseCase.createEstateType(estateType)

    @GetMapping
    fun getEstateTypeByName(@RequestParam("estateType") estateTypeName: String) = estateTypeUseCase.getEstateTypeByName(estateTypeName)

    @GetMapping("/all")
    fun getAllEstatesForOwner(pageable: Pageable) = estateTypeUseCase.getAllEstateTypes(pageable)

    @PostMapping("/update")
    fun updateEstateType(@RequestBody estateType: EstateType) = estateTypeUseCase.updateEstateType(estateType)

    @DeleteMapping
    fun deleteEstateTypeByName(@RequestParam("estateType") estateTypeName: String) = estateTypeUseCase.deleteEstateTypeByName(estateTypeName)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(ex: ConstraintViolationException): ResponseEntity<String> {
        val errors = ex.constraintViolations.joinToString(", ") { it.message }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}