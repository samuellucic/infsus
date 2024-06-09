package hr.unizg.fer.nis.domain.models

import hr.unizg.fer.nis.annotations.DefaultConstructor
import jakarta.persistence.*

@Entity
@Table(name = "Notification")
@DefaultConstructor
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long? = null,

    @Column
    val userId: String,

    @Column
    val message: String? = null,

    @Column
    val topic: String? = null,

    @Column
    val delivered: Int? = null,

    @Column
    val pid: String
)