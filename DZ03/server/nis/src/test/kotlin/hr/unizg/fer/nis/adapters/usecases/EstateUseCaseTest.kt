package hr.unizg.fer.nis.adapters.usecases

import hr.unizg.fer.nis.domain.models.Estate
import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import hr.unizg.fer.nis.ports.repositories.IEstateRepository
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateMapper
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateRequest
import hr.unizg.fer.nis.ports.usecases.requests.EstateUpdateMapper
import hr.unizg.fer.nis.ports.usecases.results.mapToEstateResult
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*

class EstateUseCaseTest {
    private val estateOwnerRepository: IEstateOwnerRepository = mock()
    private val estateRepository: IEstateRepository = mock()
    private val validator: Validator = mock()
    private val estateCreateMapper: EstateCreateMapper = mock()
    private val estateUpdateMapper: EstateUpdateMapper = mock()
    private val estateUseCase = EstateUseCase(estateRepository, estateOwnerRepository, validator, estateCreateMapper, estateUpdateMapper)

    @Test
    fun `should create estate`() {
        //given
        whenever(validator.validate(eq(estate))).thenReturn(emptySet<ConstraintViolation<Estate>>())
        whenever(estateOwnerRepository.findById(eq(1L))).thenReturn(Optional.of(owner))
        whenever(validator.validate(eq(owner))).thenReturn(emptySet<ConstraintViolation<EstateOwner>>())
        whenever(estateCreateMapper.mapToEstate(eq(estateCreateRequest))).thenReturn(estate)
        whenever(estateRepository.save(eq(estate))).thenReturn(estate)

        //when
        estateUseCase.createEstateForOwner(estateCreateRequest)

        //then
        verify(estateOwnerRepository, times(1)).findById(eq(1L))
        verify(estateRepository, times(1)).save(eq(estate))
    }

    @Test
    fun `should not create estate if validation fails`() {
        //given
        val constraintViolation = mock<ConstraintViolation<Estate>>()
        val violations: Set<ConstraintViolation<Estate>> = setOf(constraintViolation)
        whenever(estateCreateMapper.mapToEstate(eq(estateCreateRequest))).thenReturn(estate)
        whenever(validator.validate(eq(estate))).thenReturn(violations)

        //when, then
        assertThrows<ConstraintViolationException> {
            estateUseCase.createEstateForOwner(estateCreateRequest)
        }
    }

    @Test
    fun `should not create estate if given owner does not exist`() {
        //given
        whenever(validator.validate(eq(estate))).thenReturn(emptySet<ConstraintViolation<Estate>>())
        whenever(estateOwnerRepository.findById(eq(1L))).thenReturn(Optional.empty())
        whenever(estateCreateMapper.mapToEstate(eq(estateCreateRequest))).thenReturn(estate)

        //when
        assertThrows<IllegalArgumentException> {
            estateUseCase.createEstateForOwner(estateCreateRequest)
        }

        //then
        verify(estateOwnerRepository, times(1)).findById(eq(1L))
        verify(estateRepository, times(0)).save(eq(estate))
    }

    @Test
    fun `should return estate`() {
        //given
        whenever(estateRepository.findById(eq(1L))).thenReturn(Optional.of(estate))

        //when
        val estateReturned = estateUseCase.getEstateById(1L)

        //then
        assertEquals(estateReturned, estate.mapToEstateResult())
    }

    @Test
    fun `should throw exception if estate not found`() {
        //given
        whenever(estateRepository.findById(eq(1L))).thenReturn(Optional.empty())

        //when, then
        assertThrows<IllegalArgumentException> {
            estateUseCase.getEstateById(1L)
        }
    }

    val town = Town(
        id = 1L,
        name = "City city",
        region = "Region",
        postCode = "10000",
        country = "Country"
    )
    val estateType = EstateType(name = "EstateType", description = "Desc")
    val owner = EstateOwner(
        id = 1L,
        name = "Name name",
        surname = "Surname",
        address = "Address",
        email = "email@email.com",
        birthDate = Instant.now(),
        town = town
    )
    val estateCreateRequest = EstateCreateRequest(
        area = 1,
        price = 1.5,
        description = "AAAAAAAAAA",
        address = "AAAAAAAAAAA",
        estateTypeName = estateType.name,
        townId = town.id!!,
        ownerId = owner.id!!
    )
    val estate = Estate(
        id = 1L,
        area = 1,
        price = 1.5,
        description = "AAAAAAAAAA",
        address = "AAAAAAAAAAA",
        estateType = estateType,
        town = town,
        estateOwner = owner
    )
}
