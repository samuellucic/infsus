package hr.unizg.fer.nis.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import hr.unizg.fer.nis.config.IntegrationTest
import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import hr.unizg.fer.nis.ports.repositories.ITownRepository
import hr.unizg.fer.nis.ports.usecases.requests.EstateOwnerCreateRequest
import hr.unizg.fer.nis.ports.usecases.results.EstateOwnerResult
import hr.unizg.fer.nis.util.TestUtil
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@IntegrationTest
@AutoConfigureMockMvc
class EstateOwnerControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var om: ObjectMapper;

    @Autowired
    private lateinit var townRepository: ITownRepository

    @Autowired
    private lateinit var estateOwnerRepository: IEstateOwnerRepository

    private var town: Town? = null;

    @BeforeEach
    fun setup() {
        val town = Town(
            name = "City city",
            region = "Region",
            postCode = "10000",
            country = "Country"
        )
        this.town = townRepository.save(town);
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createEstateOwnerTest() {
        val estateOwnerRequest = EstateOwnerCreateRequest(
            name = "Name name",
            surname = "Surname surname",
            email = "email@gmail.com",
            birthDate = Instant.now(),
            address = "Address address",
            townId = town!!.id!!
        )

        val estateOwnerResult = om.readValue(
            mockMvc
                .perform(
                    post("/api/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(estateOwnerRequest))
                )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString,
            EstateOwnerResult::class.java
        )
        assertThat(estateOwnerResult.id).isNotNull
        assertThat(estateOwnerResult.name).isEqualTo(estateOwnerRequest.name)
        assertThat(estateOwnerResult.surname).isEqualTo(estateOwnerRequest.surname)
        assertThat(estateOwnerResult.email).isEqualTo(estateOwnerRequest.email)
        assertThat(estateOwnerResult.birthDate).isEqualTo(estateOwnerRequest.birthDate)
        assertThat(estateOwnerResult.address).isEqualTo(estateOwnerRequest.address)
        assertThat(estateOwnerResult.townId).isEqualTo(estateOwnerRequest.townId)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createEstateOwner_FailsValidationTest() {
        val estateOwnerRequest = EstateOwnerCreateRequest(
            name = "N",
            surname = "S",
            email = "e",
            birthDate = Instant.now(),
            address = "A",
            townId = town!!.id!!
        )

        mockMvc
            .perform(
                post("/api/owner")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estateOwnerRequest))
            )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createEstateOwner_failsNoFKTest() {
        val estateOwnerRequest = EstateOwnerCreateRequest(
            name = "Name name",
            surname = "Surname surname",
            email = "email@gmail.com",
            birthDate = Instant.now(),
            address = "Address address",
            townId = town!!.id!! + 1
        )

        mockMvc
            .perform(
                post("/api/owner")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estateOwnerRequest))
            )
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun getEstateOwnerTest() {
        var estateOwner = EstateOwner(
            name = "Name name",
            surname = "Surname surname",
            email = "email@gmail.com",
            birthDate = Instant.now(),
            address = "Address address",
            town = town!!
        )
        estateOwner = estateOwnerRepository.save(estateOwner)

        val estateOwnerResult = om.readValue(
            mockMvc
                .perform(
                    get("/api/owner/${estateOwner.id}")
                )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString,
            EstateOwnerResult::class.java
        )

        assertThat(estateOwnerResult.id).isNotNull()
        assertThat(estateOwnerResult.name).isEqualTo(estateOwner.name)
        assertThat(estateOwnerResult.surname).isEqualTo(estateOwner.surname)
        assertThat(estateOwnerResult.email).isEqualTo(estateOwner.email)
        assertThat(estateOwnerResult.birthDate).isEqualTo(estateOwner.birthDate)
        assertThat(estateOwnerResult.address).isEqualTo(estateOwner.address)
        assertThat(estateOwnerResult.townId).isEqualTo(estateOwner.town.id)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun getEstateOwner_NotFoundFailsTest() {
        var estateOwner = EstateOwner(
            name = "Name name",
            surname = "Surname surname",
            email = "email@gmail.com",
            birthDate = Instant.now(),
            address = "Address address",
            town = town!!
        )
        estateOwner = estateOwnerRepository.save(estateOwner)
        mockMvc
            .perform(
                get("/api/owner/${estateOwner.id!! + 1}")
            )
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun deleteEstateOwnerTest() {
        var estateOwner = EstateOwner(
            name = "Name name",
            surname = "Surname surname",
            email = "email@gmail.com",
            birthDate = Instant.now(),
            address = "Address address",
            town = town!!
        )
        estateOwner = estateOwnerRepository.save(estateOwner)

        mockMvc
            .perform(
                delete("/api/owner/${estateOwner.id}")
            )
            .andExpect(status().isOk)
        Assertions.assertFalse(estateOwnerRepository.findById(estateOwner.id!!).isPresent)
    }
}
