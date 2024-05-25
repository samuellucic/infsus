package hr.unizg.fer.nis.validation

import hr.unizg.fer.nis.domain.models.EstateOwner
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MaxEstatesValidator : ConstraintValidator<MaxEstates, EstateOwner> {

    private var maxEstates: Int = 0

    override fun initialize(constraintAnnotation: MaxEstates) {
        this.maxEstates = constraintAnnotation.max
    }

    override fun isValid(owner: EstateOwner, context: ConstraintValidatorContext): Boolean {
        return owner.estates.size <= maxEstates
    }
}
