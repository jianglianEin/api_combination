package com.jianglianein.apigateway.repository

import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class CommentTableRepository {
    private final val cardTable: String = "card"
    @Autowired
    private lateinit var jedisPool: JedisPool

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun selectAccessibleComment(cardId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            return jedis.smembers("$cardTable:$cardId")
        }
    }
}
