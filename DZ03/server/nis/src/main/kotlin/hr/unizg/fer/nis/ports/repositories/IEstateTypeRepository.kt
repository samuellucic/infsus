package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.EstateType
import org.springframework.data.jpa.repository.JpaRepository

interface IEstateTypeRepository: JpaRepository<EstateType, String>