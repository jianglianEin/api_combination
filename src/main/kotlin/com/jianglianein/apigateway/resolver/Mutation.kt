package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.CommitOutput
import com.jianglianein.apigateway.model.type.ResultOutput
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

    fun updateUser(selectionInput: SelectionInput): ResultOutput {
        logger.info { "updateUser" }

        return remotePeopleService.updateUserByPeopleService(selectionInput)
    }

    fun createTeam(selectionInput: SelectionInput): ResultOutput {
        logger.info { "createTeam" }

        return remotePeopleService.createTeam(selectionInput.teamInput!!)
    }

    fun updateTeam(selectionInput: SelectionInput): ResultOutput {
        logger.info { "updateTeam" }

        return remotePeopleService.updateTeam(selectionInput.teamInput!!)
    }

    fun createProject(selectionInput: SelectionInput): ResultOutput {
        logger.info { "createProject" }

        return remoteScrumProjectService.createProject(selectionInput.projectInput!!)
    }

    fun updateProject(selectionInput: SelectionInput): ResultOutput {
        logger.info { "updateProject" }

        return remoteScrumProjectService.updateProject(selectionInput.projectInput!!)
    }

    fun removeProject(projectId: String): ResultOutput {
        logger.info { "removeProject" }

        return remoteScrumProjectService.removeProject(projectId)
    }

    fun createBoard(selectionInput: SelectionInput): ResultOutput {
        logger.info { "createBoard" }

        return remoteScrumProjectService.createBoard(selectionInput.boardInput!!)
    }

    fun removeBoard(boardId: String): ResultOutput {
        logger.info { "removeBoard" }

        return remoteScrumProjectService.removeBoard(boardId)
    }

    fun createCard(selectionInput: SelectionInput): ResultOutput {
        logger.info { "createCard" }

        return remoteScrumProjectService.createCard(selectionInput.cardInput!!)
    }

    fun updateCard(selectionInput: SelectionInput): ResultOutput {
        logger.info { "updateCard" }

        return remoteScrumProjectService.updateCard(selectionInput.cardInput!!)
    }

    fun removeCard(cardId: String): ResultOutput {
        logger.info { "removeCard" }

        return remoteScrumProjectService.removeCard(cardId)
    }

    fun createCommit(selectionInput: SelectionInput): CommitOutput {
        logger.info { "createCommit" }

        return remoteMessageService.createCommit(selectionInput.commitInput!!)
    }

//    fun updateCommit(selectionInput: SelectionInput): ResultOutput {
//        logger.info { "createCommit" }
//
//        return remoteMessageService.createCommit(selectionInput.commitInput!!)
//    }
}