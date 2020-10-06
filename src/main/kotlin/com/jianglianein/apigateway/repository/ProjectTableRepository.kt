package com.jianglianein.apigateway.repository

import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class ProjectTableRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun selectAccessibleProject(username: String) {
        authCheckService.getAccessibleResources(username)
    }
}
