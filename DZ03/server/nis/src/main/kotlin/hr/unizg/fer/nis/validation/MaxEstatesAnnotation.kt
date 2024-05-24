package hr.unizg.fer.nis.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MaxEstatesValidator::class])
annotation class MaxEstates(
    val message: String = "Owner cannot have more than {max} estates.",
    val max: Int,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
