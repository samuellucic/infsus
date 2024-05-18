package hr.unizg.fer.nis.model

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Estate")
@DefaultConstructor
class Estate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @Min(1)
    val price: Double,

    @Column
    @Size(min = 5, max = 100, message = "Address of owner should have between 5 and 100 characters.")
    val address: String,

    @Column
    @Min(1)
    val area: Int,

    @Column
    @Size(min = 0, max = 2000, message = "Description of estate should have max 2000 characters.")
    val description: String,

    @OneToOne
    @JoinColumn(name = "ownerId")
    val estateOwner: EstateOwner,

    @ManyToOne
    @JoinColumn(name = "townId")
    val town: Town,

    @ManyToOne
    @JoinColumn(name = "estateType")
    val estateType: EstateType
)