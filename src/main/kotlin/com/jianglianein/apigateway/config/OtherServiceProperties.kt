package com.jianglianein.apigateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class OtherServiceProperties {
    @Value("\${otherservice.picture-bed.url}")
    lateinit var pictureBedUrl: String
    @Value("\${otherservice.picture-bed.token}")
    lateinit var pictureBedToken: String
}
