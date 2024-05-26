package hr.unizg.fer.nis.config

import hr.unizg.fer.nis.NisApplication
import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [NisApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedSQL
annotation class IntegrationTest()
