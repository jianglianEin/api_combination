package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.enum.FunctionNameAuth1
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.*
import com.jianglianein.apigateway.service.RemoteMessageService
import com.jianglianein.apigateway.service.RemotePeopleService
import com.jianglianein.apigateway.service.RemoteScrumProjectService
import com.jianglianein.apigateway.validator.AuthValidator
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

    @Autowired
    private lateinit var authValidator: AuthValidator


    private var logger = KotlinLogging.logger {}

    fun login(selectionInput: SelectionInput): UserOutput? {
        logger.info { "login in" }
        val functionName = FunctionNameAuth0.LOGIN.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.loginByPeopleService(selectionInput)
    }

    fun logout(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "logout" }
        val functionName = FunctionNameAuth1.LOGOUT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.logoutByPeopleService(selectionInput)
    }

    fun selectUserBySubstring(selectionInput: SelectionInput): MutableList<UserOutput>? {
        logger.info { "selectUserBySubstring" }
        val functionName = FunctionNameAuth1.SELECT_USER_BY_SUBSTRING.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.selectUserBySubstring(selectionInput.userInput!!.username!!)
    }

    fun getCommitByReceiver(selectionInput: SelectionInput): MutableList<CommitPosOutput>? {
        logger.info { "getCommitByReceiver" }
        val functionName = FunctionNameAuth1.GET_COMMIT_BY_RECEIVER.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteMessageService.getCommitByReceiver(selectionInput.commitInput!!.receiver!!)
    }
    
    fun sendEmailToInviteReceiverJoinTeam(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "sendEmailToInviteReceiverJoinTeam" }
        val functionName = FunctionNameAuth1.SEND_EMAIL_TO_INVITE_RECEIVER_JOIN_TEAM.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.sendEmailToInviteReceiver(selectionInput.emailInput!!)
    }

    fun selectTeamByUsername(selectionInput: SelectionInput): MutableList<TeamOutPut>? {
        logger.info { "selectTeamByUsername" }
        val functionName = FunctionNameAuth1.SELECT_TEAM_BY_USERNAME.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.selectTeamByUsername(selectionInput.userInput!!.username!!)
    }

    fun selectPeopleByTeamId(selectionInput: SelectionInput): MutableList<UserOutput>? {
        logger.info { "selectPeopleByTeamId" }
        val functionName = FunctionNameAuth1.SELECT_PEOPLE_BY_TEAM_ID.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.selectPeopleByTeam(selectionInput.teamInput!!.id!!)
    }

    fun selectProjectByCreator(selectionInput: SelectionInput): ArrayList<ProjectOutput>? {
        logger.info { "selectProjectByCreator" }
        val functionName = FunctionNameAuth1.SELECT_PROJECT_BY_CREATOR.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.selectProjectsByCreator(selectionInput.projectInput!!.creator!!)
    }

    fun selectBoardsByProjectId(selectionInput: SelectionInput): MutableList<BoardOutput>? {
        logger.info { "selectBoardsByProjectId" }
        val functionName = FunctionNameAuth1.SELECT_BOARDS_BY_PROJECT_ID.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.selectBoardsByProjectId(selectionInput.boardInput!!.projectId!!)
    }

    fun selectProjectById(selectionInput: SelectionInput): ProjectOutput? {
        val functionName = FunctionNameAuth1.SELECT_PROJECT_BY_ID.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.selectProjectsById(selectionInput.projectInput!!.id!!)
    }

    fun selectCardsByBoardId(selectionInput: SelectionInput): MutableList<CardOutput>? {
        logger.info { "selectCardsByBoardId" }
        val functionName = FunctionNameAuth1.SELECT_CARDS_BY_BOARD_ID.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.selectCardsByBoardId(selectionInput.cardInput!!.boardId!!)
    }

    fun selectCommentsByCardId(selectionInput: SelectionInput): MutableList<CommitTypeOutput>? {
        logger.info { "selectCommentsByCardId" }
        val functionName = FunctionNameAuth1.SELECT_COMMENTS_BY_CARD_ID.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteMessageService.selectCommentsByCardId(selectionInput.commitInput!!.cardId!!)
    }
}