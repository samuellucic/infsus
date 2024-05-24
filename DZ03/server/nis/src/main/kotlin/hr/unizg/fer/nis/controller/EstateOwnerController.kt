package hr.unizg.fer.nis.controller

import hr.unizg.fer.nis.model.EstateOwner
import hr.unizg.fer.nis.service.EstateOwnerService
import hr.unizg.fer.nis.service.TownService
import jakarta.validation.ConstraintViolationException
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/owner")
class EstateOwnerController(
    private val estateOwnerService: EstateOwnerService,
    private val townService: TownService
) {

    @PostMapping
    fun createEstateOwner(@RequestBody estateOwnerCreateRequest: EstateOwnerCreateRequest) = estateOwnerService.createEstateOwner(
        estateOwnerCreateRequest.mapToEstate(), estateOwnerCreateRequest.townId)

    @GetMapping("/{ownerId}")
    fun getEstateOwnerById(@PathVariable ownerId: Long) = estateOwnerService.getEstateOwnerById(ownerId)

    @GetMapping("/all")
    fun getAllEstateOwnersPaginated(pageable: Pageable) = estateOwnerService.getAllOwnersPaginated(pageable)

    @PostMapping("/update")
    fun updateEstateOwner(@RequestBody estateOwnerUpdateRequest: EstateOwnerUpdateRequest) = estateOwnerService.updateEstateOwner(
        estateOwnerUpdateRequest.mapToEstate()
    )

    @DeleteMapping("{ownerId}")
    fun deleteEstateOwnerById(@PathVariable ownerId: Long) = estateOwnerService.deleteEstateOwnerById(ownerId)

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
        town = townService.getTownById(this.townId)
    )

    fun EstateOwnerUpdateRequest.mapToEstate() = EstateOwner(
        id = this.id,
        name = this.name,
        surname = this.surname,
        birthDate = this.birthDate,
        address = this.address,
        email = this.email,
        town = townService.getTownById(this.townId)
    )
}