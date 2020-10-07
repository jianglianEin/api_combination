package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.BoardTableRepository
import com.jianglianein.apigateway.repository.CardTableRepository
import com.jianglianein.apigateway.repository.CommentTableRepository
import com.jianglianein.apigateway.repository.ProjectTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommentAccessPermissionCheck : PermissionCheckInterface {

    @Autowired
    private lateinit var cardTableRepository: CardTableRepository

    @Autowired
    private lateinit var boardTableRepository: BoardTableRepository

    @Autowired
    private lateinit var projectTableRepository: ProjectTableRepository

    @Autowired
    private lateinit var commentTableRepository: CommentTableRepository

    override fun check(username: String, input: SelectionInput): Boolean {
        val commentInput = input.commitInput

        return when {
            commentInput != null -> {
                checkCommentIsAccessible(username, commentInput.id!!)

            }
            else -> return false
        }
    }

    private fun checkCommentIsAccessible(username: String, commentId: String): Boolean {
        val accessibleProjects = projectTableRepository.selectAccessibleProject(username)
        val accessibleParentCards = commentTableRepository.selectAccessibleParentCards(commentId)
        accessibleParentCards?.forEach { accessibleCard ->
            cardTableRepository.selectAccessibleParentBoards(accessibleCard)?.forEach { accessibleBoard ->
                boardTableRepository.selectAccessibleParentProjects(accessibleBoard)?.forEach { accessibleProject ->
                    if (accessibleProject in accessibleProjects) return true
                }
            }
        }
        return false
    }
}