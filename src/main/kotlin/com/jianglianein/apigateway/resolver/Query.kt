package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.*
import com.jianglianein.apigateway.service.RemoteMessageService
import com.jianglianein.apigateway.service.RemotePeopleService
import com.jianglianein.apigateway.service.RemoteScrumProjectService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Query : GraphQLQueryResolver {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService

    @Autowired
    private lateinit var remoteMessageService: RemoteMessageService

    @Autowired
    private lateinit var remoteScrumProjectService: RemoteScrumProjectService


    private var logger = KotlinLogging.logger {}

    fun login(selectionInput: SelectionInput): UserOutput {
        logger.info { "login in" }

        return remotePeopleService.loginByPeopleService(selectionInput)
    }

    fun logout(username: String): ResultOutput {
        logger.info { "logout" }

        return remotePeopleService.logoutByPeopleService(username)
    }

    fun selectUserBySubstring(usernameSubstring: String): MutableList<UserOutput> {
        logger.info { "selectUserBySubstring" }

        return remotePeopleService.selectUserBySubstring(usernameSubstring)
    }

    fun getCommitByReceiver(receiver: String): MutableList<CommitOutput> {
        logger.info { "getCommitByReceiver" }

        return remoteMessageService.getCommitByReceiver(receiver)
    }
    
    fun sendEmailToInviteReceiverJoinTeam(emailInput: EmailInput): ResultOutput {
        logger.info { "sendEmailToInviteReceiverJoinTeam" }

        return remotePeopleService.sendEmailToInviteReceiver(emailInput)
    }

    fun selectTeamByUsername(username: String): MutableList<TeamOutPut> {
        logger.info { "selectTeamByUsername" }

        return remotePeopleService.selectTeamByUsername(username)
    }

    fun selectProjectByCreator(creator: String): MutableList<ProjectOutput> {
        logger.info { "selectProjectByCreator" }

        return remoteScrumProjectService.selectProjectsByCreator(creator)
    }

    fun selectBoardsByProjectId(projectId: String): MutableList<BoardOutput>{
        logger.info { "selectBoardsByProjectId" }

        return remoteScrumProjectService.selectBoardsByProjectId(projectId)
    }

    fun selectProjectById(projectId: String): ProjectOutput{

        return remoteScrumProjectService.selectProjectsById(projectId)
    }

    fun selectCardsByBoardId(boardId: String): MutableList<CardOutput>{
        logger.info { "selectCardsByBoardId" }

        return remoteScrumProjectService.selectCardsByBoardId(boardId)
    }

    fun selectCommentsByCardId(cardId: String): MutableList<CommitWithAnnouncerOutput>{
        logger.info { "selectCommentsByCardId" }

        return remoteMessageService.selectCommentsByCardId(cardId)
    }
}