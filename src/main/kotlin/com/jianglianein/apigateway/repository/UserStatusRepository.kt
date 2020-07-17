package com.jianglianein.apigateway.repository


import com.jianglianein.apigateway.poko.UserStatus
import com.microservice.peopleservice.poko.type.UserStatusType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class UserStatusRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool
    private val jwtTokenPrefix = "jwt"
    private val statusField = "status"
    private val statusValue = "jwt-token"
    private val statusUpdateTime = "updateTime"
    private val statusUser = "username"
    private val expireTime = 3600 * 12

    fun update(token: String, secure: String) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                hset("$jwtTokenPrefix:$token", "$statusField:$statusValue", secure)
                expire("$jwtTokenPrefix:$token", expireTime)
            }
        }
    }

    fun get(uid: String): UserStatus {
        var userInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            userInfo = it.hgetAll("$jwtTokenPrefix:$uid")
        }
        if (userInfo.isEmpty()) {
            return UserStatus()
        }
        val statusValue = UserStatusType.valueOf(userInfo["$statusField:$statusValue"]!!)
        val updateTime = userInfo["$statusField:$statusUpdateTime"]!!.toLong()
        val username = userInfo["$statusField:$statusUser"]!!

        return UserStatus(statusValue, username, updateTime)
    }
}
