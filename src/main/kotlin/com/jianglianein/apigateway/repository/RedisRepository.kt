package com.jianglianein.apigateway.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class RedisRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool
    private val jwtTokenPrefix = "jwt"
    private val statusField = "status"
    private val statusValue = "jwt-token"
    private val expireTime = 3600 * 12

    fun updateJwt(token: String, secure: String) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                jedis.select(3)
                hset("$jwtTokenPrefix:$token", "$statusField:$statusValue", secure)
                expire("$jwtTokenPrefix:$token", expireTime)
            }
        }
    }

    fun getJwt(token: String): String {
        var secureInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            it.select(3)
            secureInfo = it.hgetAll("$jwtTokenPrefix:$token")
        }
        if (secureInfo.isEmpty()) {
            return ""
        }
        return secureInfo["$statusField:$statusValue"]!!
    }
}
