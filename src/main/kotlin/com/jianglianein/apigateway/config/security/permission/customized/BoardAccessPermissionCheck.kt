package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.BoardTableRepository
import com.jianglianein.apigateway.repository.ProjectTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BoardAccessPermissionCheck : PermissionCheckInterface {

    @Autowired
    private lateinit var boardTableRepository: BoardTableRepository

    @Autowired
    private lateinit var projectTableRepository: ProjectTableRepository

    override fun check(username: String, input: SelectionInput): Boolean {
        val boardInput = input.boardInput
        val cardInput = input.cardInput

        return when {
            boardInput == null && cardInput == null -> {
                false
            }
            boardInput == null && cardInput != null -> {
                checkBoardIsAccessible(username, cardInput.boardId!!)

            }
            boardInput != null && cardInput == null -> {
                checkBoardIsAccessible(username, boardInput.id!!)
            }
            else -> return false
        }
    }

    private fun checkBoardIsAccessible(username: String, boardId: String): Boolean {
        val accessibleProjects = projectTableRepository.selectAccessibleProject(username)
        boardTableRepository.selectAccessibleParentProjects(boardId)?.forEach { accessibleProject ->
            if (accessibleProject in accessibleProjects) return true
        }

        return false
    }
}