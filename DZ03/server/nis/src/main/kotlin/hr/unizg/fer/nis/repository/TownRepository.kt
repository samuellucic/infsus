package hr.unizg.fer.nis.repository

import hr.unizg.fer.nis.model.Town
import org.springframework.data.jpa.repository.JpaRepository

interface TownRepository: JpaRepository<Town, Long>