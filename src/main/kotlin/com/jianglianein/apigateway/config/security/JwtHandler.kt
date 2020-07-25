package com.jianglianein.apigateway.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.jianglianein.apigateway.model.enum.JwtClaim
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtHandler {
    private var logger = KotlinLogging.logger {}

    private val expireTime = 12 * 60 * 60 * 1000.toLong()

    fun sign(claimsMap: MutableMap<String, Any>, secret: String?): String {
        val date = Date(System.currentTimeMillis() + expireTime)
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        val jwt = JWT.create()

        for ((key, value) in claimsMap){
            if (value is String){
                jwt.withClaim(key, value)
            }
            if (value is List<*>){
                jwt.withClaim(key, value)
            }
        }

        return jwt.withExpiresAt(date)
                .sign(algorithm)
    }

    fun verify(secret: String?, token: String?): Boolean {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        val verifier: JWTVerifier = JWT.require(algorithm)
                .build()
        return try {
            verifier.verify(token)
            true
        }catch (e: Exception){
            false
        }
    }

    fun parseToken(token: String): MutableMap<String, Any> {
        val jwt = JWT.decode(token)
        val claimsMap = mutableMapOf<String, Any>()

        claimsMap[JwtClaim.PROJECTS.claimName] = jwt.getClaim(JwtClaim.PROJECTS.claimName).`as`(mutableListOf<String>().javaClass)
        claimsMap[JwtClaim.BOARDS.claimName] = jwt.getClaim(JwtClaim.BOARDS.claimName).`as`(mutableListOf<String>().javaClass)
        claimsMap[JwtClaim.CARDS.claimName] = jwt.getClaim(JwtClaim.CARDS.claimName).`as`(mutableListOf<String>().javaClass)
        claimsMap[JwtClaim.COMMENT.claimName] = jwt.getClaim(JwtClaim.COMMENT.claimName).`as`(mutableListOf<String>().javaClass)
        claimsMap[JwtClaim.TEAMS.claimName] = jwt.getClaim(JwtClaim.TEAMS.claimName).`as`(mutableListOf<String>().javaClass)
        claimsMap[JwtClaim.USERNAME.claimName] = jwt.getClaim(JwtClaim.USERNAME.claimName).asString()

        return claimsMap
    }
}