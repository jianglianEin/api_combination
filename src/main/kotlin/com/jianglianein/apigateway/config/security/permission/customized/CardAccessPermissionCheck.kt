package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.BoardTableRepository
import com.jianglianein.apigateway.repository.CardTableRepository
import com.jianglianein.apigateway.repository.ProjectTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CardAccessPermissionCheck : PermissionCheckInterface {

    @Autowired
    private lateinit var cardTableRepository: CardTableRepository

    @Autowired
    private lateinit var boardTableRepository: BoardTableRepository

    @Autowired
    private lateinit var projectTableRepository: ProjectTableRepository

    override fun check(username: String, input: SelectionInput): Boolean {
        val cardInput = input.cardInput
        val commentInput = input.commitInput

        return when {
            cardInput == null && commentInput == null -> {
                false
            }
            cardInput == null && commentInput != null -> {
                checkCardIsAccessible(username, commentInput.cardId!!)

            }
            cardInput != null && commentInput == null -> {
                checkCardIsAccessible(username, cardInput.id!!)
            }
            else -> return false
        }
    }

    private fun checkCardIsAccessible(username: String, cardId: String): Boolean {
        val accessibleProjects = projectTableRepository.selectAccessibleProject(username)
        val accessibleParentBoards = cardTableRepository.selectAccessibleParentBoards(cardId)
        accessibleParentBoards?.forEach { accessibleBoard ->
            boardTableRepository.selectAccessibleParentProjects(accessibleBoard)?.forEach { accessibleProject ->
                if (accessibleProject in accessibleProjects) return true
            }
        }
        return false
    }
}