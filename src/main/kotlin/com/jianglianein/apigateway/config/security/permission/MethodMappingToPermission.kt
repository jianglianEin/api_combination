package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.config.security.permission.customized.*
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class MethodMappingToPermission {
    companion object {
        private val noResourcePermissionCheck: NoResourcePermissionCheck = NoResourcePermissionCheck()
        private val commentReceiverPermissionCheck: CommentReceiverPermissionCheck = CommentReceiverPermissionCheck()
        private val methodMap = mutableMapOf<String, PermissionCheckInterface>()

        init {
            methodMap["logout"] = noResourcePermissionCheck
            methodMap["selectUserBySubstring"] = noResourcePermissionCheck
            methodMap["getCommitByReceiver"] = commentReceiverPermissionCheck
            methodMap["selectTeamByUsername"] = noResourcePermissionCheck
            methodMap["selectPeopleByTeamId"] = noResourcePermissionCheck
            methodMap["selectProjectByCreator"] = noResourcePermissionCheck
            methodMap["selectBoardsByProjectId"] = noResourcePermissionCheck
            methodMap["selectProjectById"] = noResourcePermissionCheck
            methodMap["selectCardsByBoardId"] = noResourcePermissionCheck
            methodMap["selectCommentsByCardId"] = noResourcePermissionCheck
            methodMap["updateUser"] = noResourcePermissionCheck
            methodMap["createTeam"] = noResourcePermissionCheck
            methodMap["updateTeam"] = noResourcePermissionCheck
            methodMap["removeTeam"] = noResourcePermissionCheck
            methodMap["createProject"] = noResourcePermissionCheck
            methodMap["updateProject"] = noResourcePermissionCheck
            methodMap["removeProject"] = noResourcePermissionCheck
            methodMap["createBoard"] = noResourcePermissionCheck
            methodMap["removeBoard"] = noResourcePermissionCheck
            methodMap["createCard"] = noResourcePermissionCheck
            methodMap["updateCard"] = noResourcePermissionCheck
            methodMap["removeCard"] = noResourcePermissionCheck
            methodMap["createCommit"] = noResourcePermissionCheck
            methodMap["updateCommit"] = noResourcePermissionCheck
            methodMap["removeCommit"] = noResourcePermissionCheck
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