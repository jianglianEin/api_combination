package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.type.ResultOutput
import com.jianglianein.apigateway.repository.FunctionStatusRepository
import com.jianglianein.apigateway.repository.UserStatusRepository
import com.microservice.peopleservice.poko.type.UserStatusType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class AuthCheckService {
    @Autowired
    private lateinit var toolService: ToolService
    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository
    @Autowired
    private lateinit var functionStatusRepository: FunctionStatusRepository

    fun checkAuth0(functionName: String, response: HttpServletResponse): ResultOutput{
        if (functionName == FunctionNameAuth0.LOGIN.functionName){
            val time = System.currentTimeMillis().toString()
            val uid = toolService.encode(time + functionName + ThreadLocalRandom.current())

            val cookie: Cookie = Cookie("uid",uid);
            cookie.maxAge = 12 * 3600;
            response.addCookie(cookie);
        }
        return ResultOutput(true, "Auth0 ok")
    }

    fun checkAuth1(functionName: String,
                   uid: String,
                   username: String?,
                   teamId: String?,
                   projectId: String?,
                   boardId: String?,
                   cardId: String?,
                   commentId: String?): ResultOutput {
        val userStatue = userStatusRepository.get(uid)
        if (userStatue.value != UserStatusType.Online) {

            return ResultOutput(false, "Auth1 failed")
        }

        return when{
            username != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            teamId != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            projectId != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            boardId != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            cardId != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            commentId != null -> {
                ResultOutput(false, "Auth1 failed")
            }
            else -> {
                functionStatusRepository.update(uid, functionName)
                ResultOutput(true, "Auth1 ok")
            }
        }
    }
}