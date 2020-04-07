package com.jianglianein.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableCaching
class ApigatewayApplication

fun main(args: Array<String>) {
    runApplication<ApigatewayApplication>(*args)
}
