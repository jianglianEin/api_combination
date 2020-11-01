package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.config.security.permission.customized.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MethodMappingToPermission {
    @Autowired
    private lateinit var noResourcePermissionCheck: NoResourcePermissionCheck
    @Autowired
    private lateinit var commentReceiverPermissionCheck: CommentReceiverPermissionCheck
    @Autowired
    private lateinit var projectAccessPermissionCheck: ProjectAccessPermissionCheck
    @Autowired
    private lateinit var boardAccessPermissionCheck: BoardAccessPermissionCheck
    @Autowired
    private lateinit var cardAccessPermissionCheck: CardAccessPermissionCheck
    @Autowired
    private lateinit var commentAccessPermissionCheck: CommentAccessPermissionCheck
    @Autowired
    private lateinit var teamAccessPermissionCheck: TeamAccessPermissionCheck
    @Autowired
    private lateinit var projectCreatorPermissionCheck: ProjectCreatorPermissionCheck
    private val methodMap = mutableMapOf<String, PermissionCheckInterface>()

    @PostConstruct
    fun initMethodMap() {
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