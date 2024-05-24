package hr.unizg.fer.nis.controller

import hr.unizg.fer.nis.model.EstateType
import hr.unizg.fer.nis.service.EstateTypeService
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/estateType")
class EstateTypeController(
    private val estateTypeService: EstateTypeService
) {

    @PostMapping
    fun createEstateType(@RequestBody estateType: EstateType) = estateTypeService.createEstateType(estateType)

    @GetMapping
    fun getEstateTypeByName(@RequestParam("estateType") estateTypeName: String) = estateTypeService.getEstateTypeByName(estateTypeName)

    @GetMapping("/all")
    fun getAllEstatesForOwner(pageable: Pageable) = estateTypeService.getAllEstateTypes(pageable)

    @PostMapping("/update")
    fun updateEstateType(@RequestBody estateType: EstateType) = estateTypeService.updateEstateType(estateType)

    @DeleteMapping
    fun deleteEstateTypeByName(@RequestParam("estateType") estateTypeName: String) = estateTypeService.deleteEstateTypeByName(estateTypeName)

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