package hr.unizg.fer.nis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper
import hr.unizg.fer.nis.TestUtil
import hr.unizg.fer.nis.config.IntegrationTest
import hr.unizg.fer.nis.domain.models.Estate
import hr.unizg.fer.nis.domain.models.EstateOwner
import hr.unizg.fer.nis.domain.models.EstateType
import hr.unizg.fer.nis.domain.models.Town
import hr.unizg.fer.nis.ports.repositories.IEstateOwnerRepository
import hr.unizg.fer.nis.ports.repositories.IEstateRepository
import hr.unizg.fer.nis.ports.repositories.IEstateTypeRepository
import hr.unizg.fer.nis.ports.repositories.ITownRepository
import hr.unizg.fer.nis.ports.usecases.requests.EstateCreateRequest
import hr.unizg.fer.nis.ports.usecases.results.EstateResult
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant

@IntegrationTest
@AutoConfigureMockMvc
class EstateControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var estateTypeRepository: IEstateTypeRepository

    @Autowired
    private lateinit var estateRepository: IEstateRepository

    @Autowired
    private lateinit var estateOwnerRepository: IEstateOwnerRepository

    @Autowired
    private lateinit var townRepository: ITownRepository

    @Autowired
    private lateinit var om: ObjectMapper;

    private var owner: EstateOwner? = null
    private var town: Town? = null
    private var estateType: EstateType? = null

    @BeforeEach
    fun setup(): Unit {
        var town = Town(
            name = "City city",
            region = "Region",
            postCode = "10000",
            country = "Country"
        )
        town = townRepository.save(town);
        var owner = EstateOwner(
            name = "Name name",
            surname = "Surname",
            address = "Address",
            email = "email@email.com",
            birthDate = Instant.now(),
            town = town
        )
        owner = estateOwnerRepository.save(owner)
        var estateType = EstateType(name = "EstateType", description = "Desc")
        estateType = estateTypeRepository.save(estateType)

        this.town = town
        this.owner = owner
        this.estateType = estateType
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createEstateTest() {
        val estateRequest = EstateCreateRequest(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateTypeName = estateType!!.name,
            townId = town!!.id!!,
            ownerId = owner!!.id!!
        )

        val estateResult = om.readValue(
            mockMvc
                .perform(
                    post("/api/estate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(estateRequest))
                )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString,
            EstateResult::class.java
        )
        assertThat(estateResult.id).isNotNull
        assertThat(estateResult.estateOwnerId).isEqualTo(estateRequest.ownerId)
        assertThat(estateResult.estateType).isEqualTo(estateType)
        assertThat(estateResult.town).isEqualTo(town)
        assertThat(estateResult.area).isEqualTo(estateRequest.area)
        assertThat(estateResult.price).isEqualTo(estateRequest.price)
        assertThat(estateResult.description).isEqualTo(estateRequest.description)
        assertThat(estateResult.address).isEqualTo(estateRequest.address)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createEstate_failsNoFKTest() {
        val estateRequest = EstateCreateRequest(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateTypeName = estateType!!.name,
            townId = town!!.id!! + 1,
            ownerId = owner!!.id!! + 1
        )

        mockMvc
            .perform(
                post("/api/estate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estateRequest))
            )
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun getEstateTest() {
        var estate = Estate(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateType = estateType!!,
            estateOwner = owner!!,
            town = town!!
        )
        estate = estateRepository.save(estate)

        val estateResult = om.readValue(
            mockMvc
                .perform(
                    get("/api/estate/${estate.id}")
                )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString,
            EstateResult::class.java
        )

        assertThat(estateResult.id).isNotNull
        assertThat(estateResult.estateOwnerId).isEqualTo(owner!!.id)
        assertThat(estateResult.town.id).isEqualTo(town!!.id)
        assertThat(estateResult.estateType.name).isEqualTo(estateType!!.name)
        assertThat(estateResult.area).isEqualTo(estate.area)
        assertThat(estateResult.price).isEqualTo(estate.price)
        assertThat(estateResult.description).isEqualTo(estate.description)
        assertThat(estateResult.address).isEqualTo(estate.address)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun getEstate_NotFoundFailsTest() {
        var estate = Estate(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateType = estateType!!,
            estateOwner = owner!!,
            town = town!!
        )
        estate = estateRepository.save(estate)

        mockMvc
            .perform(
                get("/api/estate/${estate.id!! + 1}")
            )
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun deleteEstateTest() {
        var estate = Estate(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateType = estateType!!,
            estateOwner = owner!!,
            town = town!!
        )
        estate = estateRepository.save(estate)

        mockMvc
            .perform(
                delete("/api/estate/${estate.id}?ownerId=${owner!!.id}")
            )
            .andExpect(status().isOk)
        Assertions.assertFalse(estateRepository.findById(estate.id!!).isPresent)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun deleteEstate_NotFoundFailsTest() {
        var estate = Estate(
            area = 1,
            price = 1.5,
            description = "AAAAAAAAAA",
            address = "AAAAAAAAAAA",
            estateType = estateType!!,
            estateOwner = owner!!,
            town = town!!
        )
        estate = estateRepository.save(estate)

        mockMvc
            .perform(
                get("/api/estate/${estate.id!! + 1}")
            )
            .andExpect(status().isNotFound)
    }
}
