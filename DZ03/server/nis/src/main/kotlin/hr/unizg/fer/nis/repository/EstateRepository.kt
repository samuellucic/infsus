package hr.unizg.fer.nis.repository

import hr.unizg.fer.nis.model.Estate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface EstateRepository: JpaRepository<Estate, Long> {
    fun findByEstateOwnerId(ownerId: Long, pageable: Pageable): Page<Estate>

    fun deleteByEstateOwnerIdAndId(ownerId: Long, estateId: Long)
}