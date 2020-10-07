package com.jianglianein.apigateway.repository

import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool


@Repository
class TeamTableRepository {
    private final val peopleTable: String = "people"
    private final val teamTable: String = "team"
    @Autowired
    private lateinit var jedisPool: JedisPool

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun selectAccessibleTeam(username: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(0)
            return jedis.smembers("$peopleTable:$username")
        }
    }

    fun selectAccessibleUserNames(teamId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(1)
            return jedis.smembers("$teamTable:$teamId")
        }
    }
}
