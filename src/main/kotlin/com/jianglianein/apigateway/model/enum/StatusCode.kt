package com.jianglianein.apigateway.model.enum

enum class StatusCode(val code: Int) {
    SUCCESS(200),
    NO_LOGIN(401),
    NO_AUTH(403),
}