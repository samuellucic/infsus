package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequestMapper
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerUpdateRequestMapper
import hr.unizg.fer.nis.ports.usecases.results.mapToEstateOwnerResult
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*

class EstateOwnerUseCaseTest {
    private val estateOwnerRepository: IEstateOwnerRepository = mock()
    private val estateOwnerCreateRequestMapper: EstateOwnerCreateRequestMapper = mock()
    private val estateOwnerUpdateRequestMapper: EstateOwnerUpdateRequestMapper = mock()
    private val estateOwnerUseCase = EstateOwnerUseCase(estateOwnerRepository, estateOwnerCreateRequestMapper, estateOwnerUpdateRequestMapper)
    @Test
    fun `should create estate`() {
        //given
        whenever(estateOwnerCreateRequestMapper.mapToEstateOwner(eq(estateOwnerCreateRequest))).thenReturn(createdEstateOwner)
        whenever(estateOwnerRepository.save(eq(createdEstateOwner))).thenReturn(createdEstateOwner)

        //when
        estateOwnerUseCase.createEstateOwner(estateOwnerCreateRequest)

        //then
        verify(estateOwnerCreateRequestMapper, times(1)).mapToEstateOwner(eq(estateOwnerCreateRequest))
        verify(estateOwnerRepository, times(1)).save(eq(createdEstateOwner))
    }

    @Test
    fun `should not create owner if provided town does not exist`() {
        //given
        whenever(estateOwnerCreateRequestMapper.mapToEstateOwner(eq(estateOwnerCreateRequest))).thenThrow(IllegalArgumentException())

        //when, then
        assertThrows<IllegalArgumentException> {
            estateOwnerUseCase.createEstateOwner(estateOwnerCreateRequest)
        }

        verify(estateOwnerCreateRequestMapper, times(1)).mapToEstateOwner(eq(estateOwnerCreateRequest))
        verify(estateOwnerRepository, times(0)).save(any())
    }

    @Test
    fun `should not create estate owner if validation fais`() {
        //given
        val constraintViolation = mock<ConstraintViolation<*>>()
        val violations: Set<ConstraintViolation<*>> = setOf(constraintViolation)
        val exception = ConstraintViolationException(violations)
        whenever(estateOwnerCreateRequestMapper.mapToEstateOwner(eq(estateOwnerCreateRequest))).thenReturn(createdEstateOwner)
        whenever(estateOwnerRepository.save(eq(createdEstateOwner))).thenThrow(exception)

        //when, then
        assertThrows<ConstraintViolationException> {
            estateOwnerUseCase.createEstateOwner(estateOwnerCreateRequest)
        }

        verify(estateOwnerCreateRequestMapper, times(1)).mapToEstateOwner(eq(estateOwnerCreateRequest))
        verify(estateOwnerRepository, times(1)).save(eq(createdEstateOwner))
    }

    @Test
    fun `should return existing estate owner`() {
        //given
        whenever(estateOwnerRepository.findById(eq(1L))).thenReturn(Optional.of(createdEstateOwner))

        //when
        val estateOwnerReturned = estateOwnerUseCase.getEstateOwnerById(1L)

        //then
        assertEquals(estateOwnerReturned, createdEstateOwner.mapToEstateOwnerResult())
    }

    @Test
    fun `should not return any estate owner`() {
        //given
        whenever(estateOwnerRepository.findById(eq(1L))).thenReturn(Optional.empty())

        //when
        assertThrows<IllegalArgumentException> {
            estateOwnerUseCase.getEstateOwnerById(1L)
        }
    }

    val estateOwnerCreateRequest = EstateOwnerCreateRequest(
        name = "Name name",
        surname = "Surname surname",
        email = "email@gmail.com",
        birthDate = Instant.now(),
        address = "Address address",
        townId = 1L
    )

    val town = Town(
        id = 1L,
        name = "Zagreb",
        postCode = "10000",
        region = "Zagreb",
        country = "CRO"
    )

    val createdEstateOwner = EstateOwner(
        id = 1L,
        name = "Name name",
        surname = "Surname surname",
        email = "email@gmail.com",
        birthDate = Instant.now(),
        address = "Address address",
        town = town
    )
}
