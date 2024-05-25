package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.Town
import org.springframework.data.jpa.repository.JpaRepository

interface ITownRepository: JpaRepository<Town, Long>