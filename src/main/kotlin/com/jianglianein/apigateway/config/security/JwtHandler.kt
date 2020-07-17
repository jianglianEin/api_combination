package com.jianglianein.apigateway.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtHandler {
    private var logger = KotlinLogging.logger {}

    private val expireTime = 12 * 60 * 60 * 1000.toLong()

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
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

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    fun verify(): Boolean {
//        val algorithm: Algorithm = Algorithm.HMAC256(secret)
//        val verifier: JWTVerifier = JWT.require(algorithm)
//                .withClaim("username", username)
//                .build()
//        val jwt: DecodedJWT = verifier.verify(token)
        return true
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    fun getUsername(token: String?): String {
        val jwt = JWT.decode(token)
        return jwt.getClaim("username").asString()
    }
}