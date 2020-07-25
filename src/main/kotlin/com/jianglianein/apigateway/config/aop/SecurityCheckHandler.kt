package com.jianglianein.apigateway.config.aop

import com.jianglianein.apigateway.config.security.JwtHandler
import com.jianglianein.apigateway.model.enum.JwtClaim
import com.jianglianein.apigateway.model.graphql.type.AuthCheckInput
import com.jianglianein.apigateway.repository.UserStatusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SecurityCheckHandler {

    @Autowired
    private lateinit var jwtHandler: JwtHandler

    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository

    fun authCheck(token: String, authCheckInput: AuthCheckInput): Boolean {
        val secure = userStatusRepository.get(token)
        if (secure.isEmpty() || !jwtHandler.verify(secure, token)){
            return false
        }
        val claimMap = jwtHandler.parseToken(token)

        return compareJwtClaimWithInputPrimaryKey(claimMap, authCheckInput)
    }

    private fun compareJwtClaimWithInputPrimaryKey(claimMap: MutableMap<String, Any>, authCheckInput: AuthCheckInput): Boolean {
        return when {
            authCheckInput.projectId != null -> {
                (claimMap[JwtClaim.PROJECTS.claimName] as MutableList<*>).contains(authCheckInput.projectId)
            }
            authCheckInput.boardId != null -> {
                (claimMap[JwtClaim.BOARDS.claimName] as MutableList<*>).contains(authCheckInput.boardId)
            }
            authCheckInput.cardId != null -> {
                (claimMap[JwtClaim.CARDS.claimName] as MutableList<*>).contains(authCheckInput.cardId)
            }
            authCheckInput.username != null -> {
                (claimMap[JwtClaim.USERNAME.claimName] as MutableList<*>).contains(authCheckInput.username)
            }
            authCheckInput.teamId != null -> {
                (claimMap[JwtClaim.TEAMS.claimName] as MutableList<*>).contains(authCheckInput.teamId)
            }
            authCheckInput.commentId != null -> {
                (claimMap[JwtClaim.COMMENT.claimName] as MutableList<*>).contains(authCheckInput.commentId)
            }
            else -> true
        }
    }
}