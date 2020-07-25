package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jianglianein.apigateway.config.security.Unsecured
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.graphql.type.*
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

    @Unsecured
    fun login(selectionInput: SelectionInput): LoginOutput? {
        logger.info { "login in" }

        return remotePeopleService.loginByPeopleService(selectionInput)
    }

    fun logout(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "logout" }

        return remotePeopleService.logoutByPeopleService(selectionInput)
    }

    fun selectUserBySubstring(selectionInput: SelectionInput): MutableList<UserOutput>? {
        logger.info { "selectUserBySubstring" }

        return remotePeopleService.selectUserBySubstring(selectionInput.userInput!!.username!!)
    }

    fun getCommitByReceiver(selectionInput: SelectionInput): MutableList<CommitPosOutput>? {
        logger.info { "getCommitByReceiver" }

        return remoteMessageService.getCommitByReceiver(selectionInput.commitInput!!.receiver!!)
    }

    fun sendEmailToInviteReceiverJoinTeam(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "sendEmailToInviteReceiverJoinTeam" }

        return remotePeopleService.sendEmailToInviteReceiver(selectionInput.emailInput!!)
    }

    fun selectTeamByUsername(selectionInput: SelectionInput): MutableList<TeamOutPut>? {
        logger.info { "selectTeamByUsername" }

        return remotePeopleService.selectTeamByUsername(selectionInput.userInput!!.username!!)
    }

    fun selectPeopleByTeamId(selectionInput: SelectionInput): MutableList<UserOutput>? {
        logger.info { "selectPeopleByTeamId" }

        return remotePeopleService.selectPeopleByTeam(selectionInput.teamInput!!.id!!)
    }

    fun selectProjectByCreator(selectionInput: SelectionInput): ArrayList<ProjectOutput>? {
        logger.info { "selectProjectByCreator" }

        return remoteScrumProjectService.selectProjectsByCreator(selectionInput.projectInput!!.creator!!)
    }

    fun selectBoardsByProjectId(selectionInput: SelectionInput): MutableList<BoardOutput>? {
        logger.info { "selectBoardsByProjectId" }

        return remoteScrumProjectService.selectBoardsByProjectId(selectionInput.boardInput!!.projectId!!)
    }

    fun selectProjectById(selectionInput: SelectionInput): ProjectOutput? {
        logger.info { "selectProjectById" }

        return remoteScrumProjectService.selectProjectsById(selectionInput.projectInput!!.id!!)
    }

    fun selectCardsByBoardId(selectionInput: SelectionInput): MutableList<CardOutput>? {
        logger.info { "selectCardsByBoardId" }

        return remoteScrumProjectService.selectCardsByBoardId(selectionInput.cardInput!!.boardId!!)
    }

    fun selectCommentsByCardId(selectionInput: SelectionInput): MutableList<CommitTypeOutput>? {
        logger.info { "selectCommentsByCardId" }

        val commitTypeOutputList = remoteMessageService.selectCommentsByCardId(selectionInput.commitInput!!.cardId!!)
        commitTypeOutputList.sortBy { it.id }
        return commitTypeOutputList
    }
}