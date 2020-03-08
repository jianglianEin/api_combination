package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.MessageOutput
import com.jianglianein.apigateway.model.type.UserOutput
import com.jianglianein.apigateway.service.RemotePeopleService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mutation : GraphQLMutationResolver {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService


    private var logger = KotlinLogging.logger {}

    fun register(selectionInput: SelectionInput): MessageOutput {
        logger.info { "register" }

        return remotePeopleService.registerByPeopleService(selectionInput)
    }

    fun updateUser(selectionInput: SelectionInput): MessageOutput {
        logger.info { "updateUser" }

        return remotePeopleService.updateUserByPeopleService(selectionInput)
    }
}