package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.ports.usecases.IEstateOwnerUseCase
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequest
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/owner")
class EstateOwnerController(
    private val estateOwnerUseCase: IEstateOwnerUseCase,
) {

    @PostMapping
    fun createEstateOwner(@RequestBody estateOwnerCreateRequest: EstateOwnerCreateRequest) = estateOwnerUseCase.createEstateOwner(
        estateOwnerCreateRequest)

    @GetMapping("/{ownerId}")
    fun getEstateOwnerById(@PathVariable ownerId: Long) = estateOwnerUseCase.getEstateOwnerById(ownerId)

    @GetMapping("/all")
    fun getAllEstateOwnersPaginated(pageable: Pageable) = estateOwnerUseCase.getAllOwnersPaginated(pageable)

    @PostMapping("/update")
    fun updateEstateOwner(@RequestBody estateOwnerUpdateRequest: EstateOwnerUpdateRequest) = estateOwnerUseCase.updateEstateOwner(
        estateOwnerUpdateRequest
    )

    @DeleteMapping("/{ownerId}")
    fun deleteEstateOwnerById(@PathVariable ownerId: Long) = estateOwnerUseCase.deleteEstateOwnerById(ownerId)

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
