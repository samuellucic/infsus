package hr.unizg.fer.nis.configuration

import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineConfiguration

import org.camunda.community.rest.EnableCamundaRestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCamundaRestClient
@Configuration
class MyClientConfiguration() {

//    @Bean
//    fun identityService(): IdentityService {
//        return processEngine.identityService
//    }
//
//    @Bean
//    fun taskService(): TaskService {
//        return processEngine.taskService
//    }
}