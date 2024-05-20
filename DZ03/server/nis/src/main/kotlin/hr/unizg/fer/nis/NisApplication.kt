package hr.unizg.fer.nis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NisApplication

fun main(args: Array<String>) {
    runApplication<NisApplication>(*args)
}
