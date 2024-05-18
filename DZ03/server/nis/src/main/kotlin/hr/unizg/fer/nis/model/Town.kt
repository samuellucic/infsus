package hr.unizg.fer.nis.model

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Town")
@DefaultConstructor
class Town(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @Size(min = 5, max = 100, message = "Name of Town should have between 5 and 100 characters.")
    val name: String,

    @Column
    @Size(min = 2, max = 20, message = "Postcode should have between 2 and 20 characters.")
    val postCode: String,

    @Column
    @Size(min = 5, max = 100, message = "Region should have between 5 and 100 characters.")
    val region: String?,

    @Column
    @Size(min = 5, max = 100, message = "Country should have between 5 and 100 characters.")
    val country: String
)