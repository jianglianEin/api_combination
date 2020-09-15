package com.jianglianein.apigateway.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

// TODO: split value from jwt and save value by username
@Repository
class UserStatusRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool
    private val jwtTokenPrefix = "jwt"
    private val statusField = "status"
    private val statusValue = "jwt-token"
    private val expireTime = 3600 * 12

    private val teamFiled = "teams"
    private val projectFiled = "projects"
    private val cardFiled = "cards"
    private val boardFiled = "boards"
    private val commentFiled = "comment"

    fun updateJwt(token: String, secure: String) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                hset("$jwtTokenPrefix:$token", "$statusField:$statusValue", secure)
                expire("$jwtTokenPrefix:$token", expireTime)
            }
        }
    }

    fun getJwt(token: String): String {
        var secureInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            secureInfo = it.hgetAll("$jwtTokenPrefix:$token")
        }
        if (secureInfo.isEmpty()) {
            return ""
        }
        return secureInfo["$statusField:$statusValue"]!!
    }

    fun updateAccessibleResource(token: String, accessibleResources: MutableMap<String, List<String>>) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                for (accessibleResource in accessibleResources) {
                    accessibleResource.value.forEach {
                        lpush("$jwtTokenPrefix:$token-${accessibleResource.key}", it)
                    }
                }
                expire("$jwtTokenPrefix:$token", expireTime)
            }
        }
    }

    fun getAccessibleResource(token: String): MutableMap<String, List<String>> {
        val accessibleResource = mutableMapOf<String, List<String>>()
        jedisPool.resource.use {
            accessibleResource[teamFiled] = it.lrange("$jwtTokenPrefix:$token-$teamFiled", 0, -1)
            accessibleResource[projectFiled] = it.lrange("$jwtTokenPrefix:$token-$projectFiled", 0, -1)
            accessibleResource[cardFiled] = it.lrange("$jwtTokenPrefix:$token-$cardFiled", 0, -1)
            accessibleResource[boardFiled] = it.lrange("$jwtTokenPrefix:$token-$boardFiled", 0, -1)
            accessibleResource[commentFiled] = it.lrange("$jwtTokenPrefix:$token-$commentFiled", 0, -1)
        }

        return accessibleResource
    }
}
