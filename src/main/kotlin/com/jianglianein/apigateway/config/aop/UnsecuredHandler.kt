package com.jianglianein.apigateway.config.aop

import com.jianglianein.apigateway.config.security.JwtHandler
import com.jianglianein.apigateway.model.type.LoginOutput
import com.jianglianein.apigateway.repository.UserStatusRepository
import com.jianglianein.apigateway.service.ToolService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UnsecuredHandler {
    @Autowired
    private lateinit var toolService: ToolService

    @Autowired
    private lateinit var jwtHandler: JwtHandler

    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository

    fun afterReturnHandle(methodName: String?, result: Any) {
        val login = "login"
        if (login == methodName){
            afterLoginHandle(result)
        }
    }

    private fun afterLoginHandle(result: Any) {
        if (!(result as LoginOutput).userOutput.username.isNullOrEmpty()) {
            val secure = result.userOutput.password + System.currentTimeMillis()
            val claimsMap = mutableMapOf<String, Any>()
            claimsMap["username"] = result.userOutput.username!!

            val token = jwtHandler.sign(claimsMap, secure)
            userStatusRepository.update(token, secure)

            (result as LoginOutput).token = token
        }
    }
}