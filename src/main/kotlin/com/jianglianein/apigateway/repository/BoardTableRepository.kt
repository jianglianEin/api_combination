package com.jianglianein.apigateway.repository

import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class BoardTableRepository {
    private final val projectTable: String = "project"
    @Autowired
    private lateinit var jedisPool: JedisPool

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun selectAccessibleBoard(projectId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            return jedis.smembers("$projectTable:$projectId")
        }
    }
}
