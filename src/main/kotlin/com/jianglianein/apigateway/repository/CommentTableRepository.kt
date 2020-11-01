package com.jianglianein.apigateway.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class CommentTableRepository {
    private final val comment: String = "comment"
    @Autowired
    private lateinit var jedisPool: JedisPool

    fun selectAccessibleParentCards(commentId: String): MutableSet<String>? {
        jedisPool.resource.use { jedis ->
            jedis.select(1)
            return jedis.smembers("$comment:$commentId")
        }
    }
}
