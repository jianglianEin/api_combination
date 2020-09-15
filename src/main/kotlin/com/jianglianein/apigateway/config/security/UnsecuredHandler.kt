package com.jianglianein.apigateway.config.security

import com.jianglianein.apigateway.model.graphql.type.LoginOutput
import com.jianglianein.apigateway.repository.UserStatusRepository
import com.jianglianein.apigateway.service.AuthCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UnsecuredHandler {

    @Autowired
    private lateinit var jwtHandler: JwtHandler

    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository

    @Autowired
    private lateinit var authCheckService: AuthCheckService

    fun afterReturnHandle(methodName: String?, result: Any) {
        val login = "login"
        if (login == methodName){
            afterLoginHandle(result)
        }
    }

    private fun afterLoginHandle(result: Any) {
        if (!(result as LoginOutput).userOutput.username.isNullOrEmpty()) {
            val secure = result.userOutput.password + System.currentTimeMillis()
            val username = result.userOutput.username!!

            val claimsMap = mutableMapOf<String, Any>()
            val accessibleResource  = mutableMapOf<String, List<String>>()
            claimsMap["username"] = username
            accessibleResource["username"] = mutableListOf(username)
            for ((key, value) in authCheckService.getAccessibleResources(username)) {
                accessibleResource[key] = value
            }

            val token = jwtHandler.sign(claimsMap, secure)
            userStatusRepository.updateJwt(token, secure)
            userStatusRepository.updateAccessibleResource(token, accessibleResource)
            result.token = token
        }
    }
}