package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.UserOutput
import com.jianglianein.apigateway.service.RemotePeopleService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Query : GraphQLQueryResolver {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService


    private var logger = KotlinLogging.logger {}

    fun login(selectionInput: SelectionInput): UserOutput {
        logger.info { "login in" }

        return remotePeopleService.loginByPeopleService(selectionInput)
    }
}