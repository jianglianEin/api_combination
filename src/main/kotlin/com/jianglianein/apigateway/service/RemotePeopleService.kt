package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.PeopleServiceProperties
import com.jianglianein.apigateway.model.graphql.IndexPageSelectionInput
import com.jianglianein.apigateway.model.type.UserOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class RemotePeopleService {
    @Autowired
    private lateinit var peopleServiceProperties: PeopleServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var httpClientService: HttpClientService

    fun loginByPeopleService(selectionInput: IndexPageSelectionInput): UserOutput {
        val url = peopleServiceProperties.url + "/user/login"

        val params = LinkedMultiValueMap<String, String>()
        params.add("username", selectionInput.userInput?.username)
        params.add("password", selectionInput.userInput?.password)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, UserOutput::class.java)
    }
}