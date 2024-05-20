package hr.unizg.fer.nis.repository

import hr.unizg.fer.nis.model.EstateType
import org.springframework.data.jpa.repository.JpaRepository

interface EstateTypeRepository: JpaRepository<EstateType, String>