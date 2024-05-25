package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequest
import hr.unizg.fer.nis.adapters.usecases.EstateOwnerUseCase
import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import jakarta.validation.ConstraintViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/owner")
class EstateOwnerController(
    private val estateOwnerUseCase: EstateOwnerUseCase,
    private val townUseCase: TownUseCase
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

    @DeleteMapping("{ownerId}")
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

    fun EstateOwnerCreateRequest.mapToEstate() = EstateOwner(
        name = this.name,
        surname = this.surname,
        birthDate = this.birthDate,
        address = this.address,
        email = this.email,
        town = townUseCase.getTownById(this.townId)
    )
}