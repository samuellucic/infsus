package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.ports.usecases.IEstateUseCase
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateUpdateRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/estate")
class EstateController(
    private val estateUseCase: IEstateUseCase,
) {

    @GetMapping("/all")
    fun getEstates() = estateUseCase.getEstates()

    @PostMapping
    fun createEstateForOwner(@RequestBody estateCreateRequest: EstateCreateRequest) = estateUseCase.createEstateForOwner(estateCreateRequest)

    @GetMapping
    fun getAllEstatesForOwner(@RequestParam("ownerId")ownerId: Long, pageable: Pageable) = estateUseCase.getEstatesByOwnerId(ownerId, pageable)

    @PostMapping("/update")
    fun updateEstateById(@RequestParam("ownerId") ownerId: Long, @RequestBody estateUpdateRequest: EstateUpdateRequest) = estateUseCase.updateEstate(estateUpdateRequest)

    @GetMapping("/{estateId}")
    fun getEstateById(@PathVariable estateId: Long) = estateUseCase.getEstateById(estateId)

    @DeleteMapping("/{estateId}")
    fun deleteEstateById(@RequestParam("ownerId") ownerId: Long, @PathVariable estateId: Long) = estateUseCase.deleteByOwnerIdAndEstateId(ownerId, estateId)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(ex: ConstraintViolationException): ResponseEntity<String> {
        val errors = ex.constraintViolations.joinToString(", ") { it.message }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}
