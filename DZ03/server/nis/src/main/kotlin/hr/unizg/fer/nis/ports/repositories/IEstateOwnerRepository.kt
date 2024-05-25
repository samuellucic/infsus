package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.EstateOwner
import org.springframework.data.jpa.repository.JpaRepository

interface IEstateOwnerRepository: JpaRepository<EstateOwner, Long>