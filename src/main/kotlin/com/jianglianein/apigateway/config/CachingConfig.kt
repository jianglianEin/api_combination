package com.jianglianein.apigateway.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class CachingConfig {
    @Autowired
    private lateinit var redisProperties: RedisProperties

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        redisStandaloneConfiguration.password = RedisPassword.of(redisProperties.password)
        redisStandaloneConfiguration.database = 1
        return JedisConnectionFactory(redisStandaloneConfiguration)
    }

    private val timeoutInDay = 1L

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(timeoutInDay))
                .disableCachingNullValues()
    }

    @Bean
    fun cacheManager(): RedisCacheManager {
        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(cacheConfiguration())
                .transactionAware()
                .build()
    }
}