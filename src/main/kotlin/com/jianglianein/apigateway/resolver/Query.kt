package com.jianglianein.apigateway.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jianglianein.apigateway.model.dto.Message
import com.jianglianein.apigateway.model.graphql.IndexPageSelectionInput
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

    fun login(indexPageSelectionInput: IndexPageSelectionInput): UserOutput {
        logger.info { "login in" }

        return remotePeopleService.loginByPeopleService(indexPageSelectionInput)
    }
}