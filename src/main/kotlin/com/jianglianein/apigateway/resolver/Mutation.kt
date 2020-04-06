package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
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
class Mutation : GraphQLMutationResolver {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService
    @Autowired
    private lateinit var remoteScrumProjectService: RemoteScrumProjectService
    @Autowired
    private lateinit var remoteMessageService: RemoteMessageService
    @Autowired
    private lateinit var authValidator: AuthValidator


    private var logger = KotlinLogging.logger {}

    fun register(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "register" }

        return remotePeopleService.registerByPeopleService(selectionInput.userInput!!)
    }

    fun updateUser(selectionInput: SelectionInput): UserOutput? {
        logger.info { "updateUser" }
        val functionName = FunctionNameAuth1.UPDATE_USER.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.updateUserByPeopleService(selectionInput)
    }

    fun createTeam(selectionInput: SelectionInput): TeamOutPut? {
        logger.info { "createTeam" }
        val functionName = FunctionNameAuth1.CREATE_TEAM.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.createTeam(selectionInput.teamInput!!)
    }

    fun updateTeam(selectionInput: SelectionInput): TeamOutPut? {
        logger.info { "updateTeam" }
        val functionName = FunctionNameAuth1.UPDATE_TEAM.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remotePeopleService.updateTeam(selectionInput.teamInput!!)
    }

    fun createProject(selectionInput: SelectionInput): ProjectOutput? {
        logger.info { "createProject" }
        val functionName = FunctionNameAuth1.CREATE_PROJECT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.createProject(selectionInput.projectInput!!)
    }

    fun updateProject(selectionInput: SelectionInput): ProjectOutput? {
        logger.info { "updateProject" }
        val functionName = FunctionNameAuth1.UPDATE_PROJECT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.updateProject(selectionInput.projectInput!!)
    }

    fun removeProject(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "removeProject" }
        val functionName = FunctionNameAuth1.REMOVE_PROJECT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.removeProject(selectionInput.projectInput!!.id!!)
    }

    fun createBoard(selectionInput: SelectionInput): BoardOutput? {
        logger.info { "createBoard" }
        val functionName = FunctionNameAuth1.CREATE_BOARD.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.createBoard(selectionInput.boardInput!!)
    }

    fun removeBoard(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "removeBoard" }
        val functionName = FunctionNameAuth1.REMOVE_BOARD.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.removeBoard(selectionInput.boardInput!!.id!!)
    }

    fun createCard(selectionInput: SelectionInput): CardOutput? {
        logger.info { "createCard" }
        val functionName = FunctionNameAuth1.CREATE_CARD.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.createCard(selectionInput.cardInput!!)
    }

    fun updateCard(selectionInput: SelectionInput): CardOutput? {
        logger.info { "updateCard" }
        val functionName = FunctionNameAuth1.UPDATE_CARD.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.updateCard(selectionInput.cardInput!!)
    }

    fun removeCard(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "removeCard" }
        val functionName = FunctionNameAuth1.REMOVE_CARD.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteScrumProjectService.removeCard(selectionInput.cardInput!!.id!!)
    }

    fun createCommit(selectionInput: SelectionInput): CommitTypeOutput? {
        logger.info { "createCommit" }
        val functionName = FunctionNameAuth1.CREATE_COMMIT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteMessageService.createCommit(selectionInput.commitInput!!)
    }

    fun updateCommit(selectionInput: SelectionInput): CommitTypeOutput? {
        logger.info { "updateCommit" }
        val functionName = FunctionNameAuth1.UPDATE_COMMIT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteMessageService.updateCommit(selectionInput.commitInput!!)
    }

    fun removeCommit(selectionInput: SelectionInput): ResultOutput? {
        logger.info { "removeCommit" }
        val functionName = FunctionNameAuth1.REMOVE_COMMIT.functionName
        if (!authValidator.checkFunctionAuth(selectionInput.uid!!, functionName)){
            return null
        }

        return remoteMessageService.removeCommit(selectionInput.commitInput!!.id!!)
    }
}