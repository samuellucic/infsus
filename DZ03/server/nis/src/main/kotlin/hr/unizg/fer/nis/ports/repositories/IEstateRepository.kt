package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.Estate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface IEstateRepository: JpaRepository<Estate, Long> {
    fun findByEstateOwnerId(ownerId: Long, pageable: Pageable): Page<Estate>

    fun deleteByEstateOwnerIdAndId(ownerId: Long, estateId: Long)
}