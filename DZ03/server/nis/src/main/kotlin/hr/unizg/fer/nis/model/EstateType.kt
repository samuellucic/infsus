package hr.unizg.fer.nis.model

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size

@Entity
@Table(name = "EstateType")
@DefaultConstructor
class EstateType(
    @Id
    @Column
    @Size(min = 2, max = 100, message = "Estate type should have between 2 and 100 characters.")
    val name: String,

    @Column
    @Size(min = 0, max = 2000, message = "Description of estate type should have max 2000 characters.")
    val description: String?
)