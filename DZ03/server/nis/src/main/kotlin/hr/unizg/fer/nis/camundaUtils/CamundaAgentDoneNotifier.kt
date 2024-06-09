package hr.unizg.fer.nis.camundaUtils

import hr.unizg.fer.nis.domain.models.Notification
import hr.unizg.fer.nis.ports.repositories.INotificationRepository
import mu.KotlinLogging
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("NotifyAgentTour")
class CamundaAgentDoneNotifier(
    private val notificationRepository: INotificationRepository
): ExternalTaskHandler {
    private val log = KotlinLogging.logger {}

    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        val notification = Notification(
            userId = externalTask?.getVariable("agentId") as String,
            message = "You have been assigned as agent for tour with buyer with buyerId: ${externalTask.getVariable<String>("buyerId")} " +
                    "and estateId: ${externalTask.getVariable<String>("estateId")} " +
                    "and time: ${externalTask.getVariable<String>("TourDateTime")}",
            topic = externalTask.topicName,
            delivered = 0,
            pid = externalTask.processInstanceId
        )
        notificationRepository.save(notification)
        log.info { "sending email for notification to agent ${externalTask?.getVariable<String>("agentId")}" }
        externalTaskService?.complete(externalTask)
    }
}