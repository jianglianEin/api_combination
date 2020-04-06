package com.jianglianein.apigateway.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class FunctionStatusRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool
    private val functionUidPrefix = "functionUid"
    private val statusField = "status"
    private val expireTime = 20

    fun update(uid: String, function: String) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                hset("$functionUidPrefix:$uid$function", statusField, true.toString())
                expire("$functionUidPrefix:$uid$function", expireTime)
            }
        }
    }

    fun get(uid: String, function: String): Boolean {
        var functionInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            functionInfo = it.hgetAll("$functionUidPrefix:$uid$function")
        }
        if (functionInfo.isEmpty()) {
            return false
        }
        val status = functionInfo[statusField]!!.toBoolean()

        return status
    }
}
