package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.config.security.permission.customized.*
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class MethodMappingToPermission {
    companion object {
        private val noResourcePermissionCheck: NoResourcePermissionCheck = NoResourcePermissionCheck()
        private val commentResourcePermissionCheck: CommentResourcePermissionCheck = CommentResourcePermissionCheck()
        private val userResourcePermissionCheck: UserResourcePermissionCheck = UserResourcePermissionCheck()
        private val teamResourcePermissionCheck: TeamResourcePermissionCheck = TeamResourcePermissionCheck()
        private val projectResourcePermissionCheck: ProjectResourcePermissionCheck = ProjectResourcePermissionCheck()
        private val boardResourcePermissionCheck: BoardResourcePermissionCheck = BoardResourcePermissionCheck()
        private val cardResourcePermissionCheck: CardResourcePermissionCheck = CardResourcePermissionCheck()
        private val methodMap = mutableMapOf<String, PermissionCheckInterface>()

        init {
            methodMap["logout"] = noResourcePermissionCheck
            methodMap["selectUserBySubstring"] = noResourcePermissionCheck
            methodMap["getCommitByReceiver"] = commentResourcePermissionCheck
            methodMap["selectTeamByUsername"] = userResourcePermissionCheck
            methodMap["selectPeopleByTeamId"] = teamResourcePermissionCheck

            methodMap["selectProjectByCreator"] = noResourcePermissionCheck

            methodMap["selectBoardsByProjectId"] = projectResourcePermissionCheck
            methodMap["selectProjectById"] = projectResourcePermissionCheck
            methodMap["selectCardsByBoardId"] = boardResourcePermissionCheck
            methodMap["selectCommentsByCardId"] = cardResourcePermissionCheck
            methodMap["updateUser"] = userResourcePermissionCheck
            methodMap["createTeam"] = noResourcePermissionCheck
            methodMap["updateTeam"] = teamResourcePermissionCheck
            methodMap["removeTeam"] = teamResourcePermissionCheck
            methodMap["createProject"] = noResourcePermissionCheck
            methodMap["updateProject"] = projectResourcePermissionCheck
            methodMap["removeProject"] = projectResourcePermissionCheck
            methodMap["createBoard"] = boardResourcePermissionCheck
            methodMap["removeBoard"] = boardResourcePermissionCheck
            methodMap["createCard"] = cardResourcePermissionCheck
            methodMap["updateCard"] = cardResourcePermissionCheck
            methodMap["removeCard"] = cardResourcePermissionCheck
            methodMap["createCommit"] = commentResourcePermissionCheck
            methodMap["updateCommit"] = commentResourcePermissionCheck
            methodMap["removeCommit"] = commentResourcePermissionCheck
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