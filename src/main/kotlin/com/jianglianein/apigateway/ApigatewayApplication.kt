package com.jianglianein.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class ApigatewayApplication

fun main(args: Array<String>) {
    runApplication<ApigatewayApplication>(*args)
}
