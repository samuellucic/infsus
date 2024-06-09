package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface INotificationRepository: JpaRepository<Notification, Long> {
    fun findByUserIdAndDelivered(userId: String, delivered: Int): List<Notification>
}