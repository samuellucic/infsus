package hr.unizg.fer.nis.controller

import hr.unizg.fer.nis.model.Town
import hr.unizg.fer.nis.service.TownService
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
@RequestMapping("/api/town")
class TownController(
    private val townService: TownService
) {
    @PostMapping
    fun createTown(@RequestBody town: Town) = townService.createTown(town)

    @GetMapping("/all")
    fun getAllTownsPaginated(pageable: Pageable) = townService.getAllTownsPaginated(pageable)

    @GetMapping("/{townId}")
    fun getTownById(@PathVariable townId: Long) = townService.getTownById(townId)

    @PostMapping("/update")
    fun updateTown(@RequestBody town: Town) = townService.updateTown(town)

    @DeleteMapping("/{townId}")
    fun deleteTownById(@PathVariable townId: Long) = townService.deleteTownById(townId)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}