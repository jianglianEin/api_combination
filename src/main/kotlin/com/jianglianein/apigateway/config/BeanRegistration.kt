package com.jianglianein.apigateway.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Configuration
class BeanRegistration {
    @Autowired
    private lateinit var redisProperties: RedisProperties

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false)
        return objectMapper
    }

    @SuppressWarnings("MagicNumber")
    @Bean
    fun jedisPool(): JedisPool {
        val jedisPoolConfig = JedisPoolConfig()
        jedisPoolConfig.maxTotal = 100
        val timeout = redisProperties.timeout.toMillis().toInt()
        return JedisPool(jedisPoolConfig, redisProperties.host, redisProperties.port, timeout, redisProperties.password)
    }
}