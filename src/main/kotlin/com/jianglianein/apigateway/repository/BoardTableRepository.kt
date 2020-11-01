package com.jianglianein.apigateway.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class BoardTableRepository {
    private final val boardTable: String = "board"

    @Autowired
    private lateinit var jedisPool: JedisPool

    fun selectAccessibleSonCards(boardId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(0)
            return jedis.smembers("$boardTable:$boardId")
        }
    }

    fun selectAccessibleParentProjects(boardId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(1)
            return jedis.smembers("$boardTable:$boardId")
        }
    }
}
