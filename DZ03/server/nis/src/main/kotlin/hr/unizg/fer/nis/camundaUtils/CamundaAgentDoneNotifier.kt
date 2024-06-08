package hr.unizg.fer.nis.camundaUtils

import mu.KotlinLogging
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("NotifyAgentTour")
class CamundaAgentDoneNotifier: ExternalTaskHandler {
    private val log = KotlinLogging.logger {}

    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        log.info { "sending email for notification to agent ${externalTask?.getVariable<String>("agentId")}" }
        externalTaskService?.complete(externalTask)
    }
}