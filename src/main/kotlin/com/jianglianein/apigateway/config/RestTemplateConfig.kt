package com.jianglianein.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets


@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(factory: ClientHttpRequestFactory?): RestTemplate {
        val restTemplate = RestTemplate(factory!!)
        restTemplate.messageConverters.set(1, StringHttpMessageConverter(StandardCharsets.UTF_8))
        return restTemplate
    }

    @Bean
    fun simpleClientHttpRequestFactory(): ClientHttpRequestFactory {
        val factory = SimpleClientHttpRequestFactory()
        factory.setReadTimeout(10000) //ms
        factory.setConnectTimeout(15000) //ms
        return factory
    }
}
