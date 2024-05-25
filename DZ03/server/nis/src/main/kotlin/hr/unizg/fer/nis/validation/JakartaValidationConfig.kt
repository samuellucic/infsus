package hr.unizg.fer.nis.validation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor

@Configuration
class ValidationConfig {
    @Bean
    fun methodValidationPostProcessor() = MethodValidationPostProcessor()
}