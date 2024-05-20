package hr.unizg.fer.nis.repository

import hr.unizg.fer.nis.model.EstateOwner
import org.springframework.data.jpa.repository.JpaRepository

interface EstateOwnerRepository: JpaRepository<EstateOwner, Long>