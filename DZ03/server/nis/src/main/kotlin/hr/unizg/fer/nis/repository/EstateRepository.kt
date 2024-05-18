package hr.unizg.fer.nis.repository

import hr.unizg.fer.nis.model.Estate
import org.springframework.data.jpa.repository.JpaRepository

interface EstateRepository: JpaRepository<Estate, Long>