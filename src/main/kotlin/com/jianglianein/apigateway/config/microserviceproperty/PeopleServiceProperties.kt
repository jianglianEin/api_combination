package com.jianglianein.apigateway.config.microserviceproperty

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class PeopleServiceProperties {
    @Value("\${microservice.peopleservice.url}")
    lateinit var url: String
}
