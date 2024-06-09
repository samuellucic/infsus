package hr.unizg.fer.nis.domain.models

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Town")
@DefaultConstructor
data class Town(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @field:Size(min = 5, max = 100, message = "Name of Town should have between 5 and 100 characters.")
    val name: String,

    @Column
    @field:Size(min = 2, max = 20, message = "Postcode should have between 2 and 20 characters.")
    val postCode: String,

    @Column
    @field:Size(min = 5, max = 100, message = "Region should have between 5 and 100 characters.")
    val region: String?,

    @Column
    @field:Size(min = 5, max = 100, message = "Country should have between 5 and 100 characters.")
    val country: String
)
