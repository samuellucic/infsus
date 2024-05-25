package hr.unizg.fer.nis.controllers

import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.adapters.usecases.TownUseCase
import hr.unizg.fer.nis.ports.usecases.ITownUseCase
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
@RequestMapping("/api/town")
class TownController(
    private val townUseCase: ITownUseCase
) {
    @PostMapping
    fun createTown(@RequestBody town: Town) = townUseCase.createTown(town)

    @GetMapping("/all")
    fun getAllTownsPaginated(pageable: Pageable) = townUseCase.getAllTownsPaginated(pageable)

    @GetMapping("/{townId}")
    fun getTownById(@PathVariable townId: Long) = townUseCase.getTownById(townId)

    @PostMapping("/update")
    fun updateTown(@RequestBody town: Town) = townUseCase.updateTown(town)

    @DeleteMapping("/{townId}")
    fun deleteTownById(@PathVariable townId: Long) = townUseCase.deleteTownById(townId)

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
