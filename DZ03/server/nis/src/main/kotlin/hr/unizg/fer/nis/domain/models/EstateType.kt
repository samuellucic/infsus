package hr.unizg.fer.nis.domain.models

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size

@Entity
@Table(name = "EstateType")
@DefaultConstructor
data class EstateType(
    @Id
    @Column
    @field:Size(min = 2, max = 100, message = "Estate type should have between 2 and 100 characters.")
    val name: String,

    @Column
    @field:Size(min = 0, max = 2000, message = "Description of estate type should have max 2000 characters.")
    val description: String?
)