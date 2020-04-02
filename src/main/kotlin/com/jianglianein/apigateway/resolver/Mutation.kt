package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.*
import com.jianglianein.apigateway.service.RemoteMessageService
import com.jianglianein.apigateway.service.RemotePeopleService
import com.jianglianein.apigateway.service.RemoteScrumProjectService
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


    private var logger = KotlinLogging.logger {}

    fun register(selectionInput: SelectionInput): ResultOutput {
        logger.info { "register" }

        return remotePeopleService.registerByPeopleService(selectionInput.userInput!!)
    }

    fun updateUser(selectionInput: SelectionInput): UserOutput {
        logger.info { "updateUser" }

        return remotePeopleService.updateUserByPeopleService(selectionInput)
    }

    fun createTeam(selectionInput: SelectionInput): TeamOutPut {
        logger.info { "createTeam" }

        return remotePeopleService.createTeam(selectionInput.teamInput!!)
    }

    fun updateTeam(selectionInput: SelectionInput): TeamOutPut {
        logger.info { "updateTeam" }

        return remotePeopleService.updateTeam(selectionInput.teamInput!!)
    }

    fun createProject(selectionInput: SelectionInput): ProjectOutput {
        logger.info { "createProject" }

        return remoteScrumProjectService.createProject(selectionInput.projectInput!!)
    }

    fun updateProject(selectionInput: SelectionInput): ProjectOutput {
        logger.info { "updateProject" }

        return remoteScrumProjectService.updateProject(selectionInput.projectInput!!)
    }

    fun removeProject(selectionInput: SelectionInput): ResultOutput {
        logger.info { "removeProject" }

        return remoteScrumProjectService.removeProject(selectionInput.projectInput!!.id!!)
    }

    fun createBoard(selectionInput: SelectionInput): BoardOutput {
        logger.info { "createBoard" }

        return remoteScrumProjectService.createBoard(selectionInput.boardInput!!)
    }

    fun removeBoard(selectionInput: SelectionInput): ResultOutput {
        logger.info { "removeBoard" }

        return remoteScrumProjectService.removeBoard(selectionInput.boardInput!!.id!!)
    }

    fun createCard(selectionInput: SelectionInput): CardOutput {
        logger.info { "createCard" }

        return remoteScrumProjectService.createCard(selectionInput.cardInput!!)
    }

    fun updateCard(selectionInput: SelectionInput): CardOutput {
        logger.info { "updateCard" }

        return remoteScrumProjectService.updateCard(selectionInput.cardInput!!)
    }

    fun removeCard(selectionInput: SelectionInput): ResultOutput {
        logger.info { "removeCard" }

        return remoteScrumProjectService.removeCard(selectionInput.cardInput!!.id!!)
    }

    fun createCommit(selectionInput: SelectionInput): CommitTypeOutput {
        logger.info { "createCommit" }

        return remoteMessageService.createCommit(selectionInput.commitInput!!)
    }

    fun updateCommit(selectionInput: SelectionInput): CommitTypeOutput {
        logger.info { "updateCommit" }

        return remoteMessageService.updateCommit(selectionInput.commitInput!!)
    }

    fun removeCommit(selectionInput: SelectionInput): ResultOutput {
        logger.info { "removeCommit" }

        return remoteMessageService.removeCommit(selectionInput.commitInput!!.id!!)
    }
}