package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.ResultOutput
import com.jianglianein.apigateway.service.RemotePeopleService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mutation : GraphQLMutationResolver {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService


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
}