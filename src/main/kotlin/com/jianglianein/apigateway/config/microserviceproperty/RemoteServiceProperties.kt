package com.jianglianein.apigateway.config.microserviceproperty

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RemoteServiceProperties {
    @Value("\${microservice.peopleservice.url}")
    lateinit var peopleServiceUrl: String
    @Value("\${microservice.messageservice.url}")
    lateinit var messageServiceUrl: String
}
