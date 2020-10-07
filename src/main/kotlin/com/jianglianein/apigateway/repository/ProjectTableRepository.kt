package com.jianglianein.apigateway.repository

import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class ProjectTableRepository {

    private final val projectTable: String = "project"

    @Autowired
    private lateinit var jedisPool: JedisPool

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun selectAccessibleProject(username: String): MutableList<String> {
        return authCheckService.getAccessibleResources(username)
    }

    fun selectAccessibleSonBoards(projectId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(0)
            return jedis.smembers("$projectTable:$projectId")
        }
    }
}
