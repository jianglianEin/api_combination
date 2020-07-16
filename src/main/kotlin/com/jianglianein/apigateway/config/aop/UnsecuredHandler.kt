package com.jianglianein.apigateway.config.aop

import com.jianglianein.apigateway.config.security.iauthentication.IAuthentication
import com.jianglianein.apigateway.model.type.UserOutput
import com.jianglianein.apigateway.service.ToolService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom

@Component
class UnsecuredHandler {
    @Autowired
    private lateinit var toolService: ToolService

    fun afterReturnHandle(methodName: String?, result: Any) {
        val login = "login"
        if (login == methodName && !(result as UserOutput).username.isNullOrEmpty()){
            val authorities = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
            val time = System.currentTimeMillis().toString()
            val uid = toolService.encode(time + result.username + ThreadLocalRandom.current())
            val principal = mutableMapOf<String, String?>()
            principal["uid"] = uid
            principal["username"] = result.username
            val credentials = "testCredentials"

            SecurityContextHolder.getContext().authentication = IAuthentication(principal, credentials, authorities)
        }
    }
}