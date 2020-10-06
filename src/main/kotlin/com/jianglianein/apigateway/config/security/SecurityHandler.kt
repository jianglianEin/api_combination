package com.jianglianein.apigateway.config.security

import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.RedisRepository
import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SecurityHandler {

    @Autowired
    private lateinit var jwtHandler: JwtHandler

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    @Autowired
    private lateinit var redisRepository: RedisRepository

    fun authCheck(token: String, input: SelectionInput, methodName: String): Boolean {
        val secure = redisRepository.getJwt(token)
        if (secure.isEmpty() || !jwtHandler.verify(secure, token)){
            return false
        }
        val username = jwtHandler.parseToken(token)["username"]!!
//        val accessibleResource = authCheckService.getAccessibleResources()

        return authCheckService.permissionCheck(username, input, methodName)
    }
}