package hr.unizg.fer.nis.domain.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import hr.unizg.fer.nis.annotations.DefaultConstructor
import hr.unizg.fer.nis.validation.MaxEstates
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.Instant

const val MAX_ESTATES = 5

@Entity
@Table(name = "EstateOwner")
@DefaultConstructor
@MaxEstates(max = MAX_ESTATES, message = "Owner cannot have more than 5 estates.")
data class EstateOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @field:Size(min = 2, max = 100, message = "Name of owner should have between 2 and 100 characters.")
    val name: String,

    @Column
    @field:Size(min = 2, max = 100, message = "Surname of owner should have between 2 and 100 characters.")
    val surname: String,

    @Column
    val birthDate: Instant?,

    @Column
    @field:Size(min = 5, max = 100, message = "Address of owner should have between 5 and 100 characters.")
    val address: String,

    @Column
    @field:Email
    val email: String,

    @ManyToOne
    @JoinColumn(name = "townId")
    var town: Town,

    @OneToMany(mappedBy = "estateOwner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val estates: MutableList<Estate> = mutableListOf()
)