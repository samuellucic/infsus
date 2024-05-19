package hr.unizg.fer.nis.controller

import hr.unizg.fer.nis.model.Estate
import hr.unizg.fer.nis.service.EstateOwnerService
import hr.unizg.fer.nis.service.EstateService
import hr.unizg.fer.nis.service.EstateTypeService
import hr.unizg.fer.nis.service.TownService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/estate")
class EstateController(
    private val estateService: EstateService,
    private val estateOwnerService: EstateOwnerService,
    private val townService: TownService,
    private val estateTypeService: EstateTypeService
) {

    @PostMapping
    fun createEstateForOwner(@RequestBody estateCreateRequest: EstateCreateRequest) = estateService.createEstateForOwner(estateCreateRequest.mapToEstate())

    @GetMapping
    fun getAllEstatesForOwner(@RequestParam("ownerId")ownerId: Long, pageable: Pageable) = estateService.getEstatesByOwnerId(ownerId, pageable)

    @PostMapping("/update")
    fun updateEstateById(@RequestParam("ownerId") ownerId: Long, @RequestBody estateUpdateRequest: EstateUpdateRequest) = estateService.updateEstate(estateUpdateRequest.mapToEstate())

    @DeleteMapping("/{estateId}")
    fun deleteEstateById(@RequestParam("ownerId") ownerId: Long, @PathVariable estateId: Long) = estateService.deleteByOwnerIdAndEstateId(ownerId, estateId)


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    fun EstateUpdateRequest.mapToEstate() = Estate(
        id = this.id,
        address = this.address,
        area = this.area,
        price = this.price,
        description = this.description,
        estateOwner = estateOwnerService.getEstateOwnerById(this.ownerId),
        town = townService.getTownById(this.townId),
        estateType = estateTypeService.getEstateTypeByName(this.estateTypeName),
    )

    fun EstateCreateRequest.mapToEstate() = Estate(
        price = this.price,
        address = this.address,
        area = this.area,
        description = this.description,
        estateOwner = estateOwnerService.getEstateOwnerById(this.ownerId),
        town = townService.getTownById(this.townId),
        estateType = estateTypeService.getEstateTypeByName(this.estateTypeName)
    )
}