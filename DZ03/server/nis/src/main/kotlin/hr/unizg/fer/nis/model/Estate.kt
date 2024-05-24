package hr.unizg.fer.nis.model

import com.fasterxml.jackson.annotation.JsonBackReference
import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Estate")
@DefaultConstructor
data class Estate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @field:Min(1)
    val price: Double,

    @Column
    @field:Size(min = 5, max = 100, message = "Address of owner should have between 5 and 100 characters.")
    val address: String,

    @Column
    @field:Min(1)
    val area: Int,

    @Column
    @field:Size(min = 0, max = 2000, message = "Description of estate should have max 2000 characters.")
    val description: String,

    @ManyToOne
    @JoinColumn(name = "ownerId")
    @JsonBackReference
    var estateOwner: EstateOwner,

    @ManyToOne
    @JoinColumn(name = "townId")
    var town: Town,

    @ManyToOne
    @JoinColumn(name = "estateType")
    var estateType: EstateType
)