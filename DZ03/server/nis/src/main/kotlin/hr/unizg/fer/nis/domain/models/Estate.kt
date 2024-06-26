package hr.unizg.fer.nis.domain.models

import com.fasterxml.jackson.annotation.JsonBackReference
import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    @field:Min(1, message = "Price of estate should be greater than 0.")
    val price: Double,

    @Column
    @field:Size(min = 5, max = 100, message = "Address of owner should have between 5 and 100 characters.")
    val address: String,

    @Column
    @field:Min(1, message = "Area of estate should be greater than 0.")
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
