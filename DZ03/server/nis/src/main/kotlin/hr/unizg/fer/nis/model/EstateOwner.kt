package hr.unizg.fer.nis.model

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "EstateOwner")
@DefaultConstructor
class EstateOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    @Size(min = 2, max = 100, message = "Name of owner should have between 2 and 100 characters.")
    val name: String,

    @Column
    @Size(min = 2, max = 100, message = "Surname of owner should have between 2 and 100 characters.")
    val surname: String,

    @Column
    val birthDate: Instant?,

    @Column
    @Size(min = 5, max = 100, message = "Address of owner should have between 5 and 100 characters.")
    val address: String,

    @Column
    @Email
    val email: String,

    @ManyToOne
    @JoinColumn(name = "townId")
    val town: Town
)