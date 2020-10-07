package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.config.security.permission.customized.*
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class MethodMappingToPermission {
    companion object {
        private val noResourcePermissionCheck: NoResourcePermissionCheck = NoResourcePermissionCheck()
        private val commentReceiverPermissionCheck: CommentReceiverPermissionCheck = CommentReceiverPermissionCheck()
        private val projectAccessPermissionCheck: ProjectAccessPermissionCheck = ProjectAccessPermissionCheck()
        private val boardAccessPermissionCheck: BoardAccessPermissionCheck = BoardAccessPermissionCheck()
        private val cardAccessPermissionCheck: CardAccessPermissionCheck = CardAccessPermissionCheck()
        private val commentAccessPermissionCheck: CommentAccessPermissionCheck = CommentAccessPermissionCheck()
        private val teamAccessPermissionCheck: TeamAccessPermissionCheck = TeamAccessPermissionCheck()
        private val projectCreatorPermissionCheck: ProjectCreatorPermissionCheck = ProjectCreatorPermissionCheck()
        private val methodMap = mutableMapOf<String, PermissionCheckInterface>()

        init {
            methodMap["logout"] = noResourcePermissionCheck
            methodMap["selectUserBySubstring"] = noResourcePermissionCheck
            methodMap["getCommitByReceiver"] = commentReceiverPermissionCheck
            methodMap["selectTeamByUsername"] = noResourcePermissionCheck
            methodMap["selectPeopleByTeamId"] = noResourcePermissionCheck
            methodMap["selectProjectByCreator"] = projectCreatorPermissionCheck
            methodMap["selectBoardsByProjectId"] = projectAccessPermissionCheck
            methodMap["selectProjectById"] = projectAccessPermissionCheck
            methodMap["selectCardsByBoardId"] = boardAccessPermissionCheck
            methodMap["selectCommentsByCardId"] = cardAccessPermissionCheck
            methodMap["updateUser"] = noResourcePermissionCheck
            methodMap["createTeam"] = noResourcePermissionCheck
            methodMap["updateTeam"] = teamAccessPermissionCheck
            methodMap["removeTeam"] = teamAccessPermissionCheck
            methodMap["createProject"] = noResourcePermissionCheck
            methodMap["updateProject"] = projectAccessPermissionCheck
            methodMap["removeProject"] = projectAccessPermissionCheck
            methodMap["createBoard"] = projectAccessPermissionCheck
            methodMap["removeBoard"] = boardAccessPermissionCheck
            methodMap["createCard"] = boardAccessPermissionCheck
            methodMap["updateCard"] = cardAccessPermissionCheck
            methodMap["removeCard"] = cardAccessPermissionCheck
            methodMap["createCommit"] = cardAccessPermissionCheck
            methodMap["updateCommit"] = commentAccessPermissionCheck
            methodMap["removeCommit"] = commentAccessPermissionCheck
        }

        fun mappingToPermission(methodName: String): PermissionCheckInterface {
            val permissionCheck = methodMap[methodName]
            if (permissionCheck == null) {
                throw AccessDeniedException("User not authenticated")
            } else {
                return permissionCheck
            }
        }
    }
}