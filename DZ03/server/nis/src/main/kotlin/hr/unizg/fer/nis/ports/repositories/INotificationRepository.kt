package hr.unizg.fer.nis.ports.repositories

import hr.unizg.fer.nis.domain.models.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface INotificationRepository: JpaRepository<Notification, Long> {
    fun findByUserIdAndDeliveredAndTopic(userId: String, delivered: Int, topic: String): List<Notification>
}