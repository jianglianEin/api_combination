package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.PeopleServiceProperties
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.type.MessageOutput
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

    fun loginByPeopleService(selectionInput: SelectionInput): UserOutput {
        val url = peopleServiceProperties.url + "/user/login"

        val params = LinkedMultiValueMap<String, String>()
        params.add("username", selectionInput.userInput?.username)
        params.add("password", selectionInput.userInput?.password)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, UserOutput::class.java)
    }

    fun logoutByPeopleService(username: String): MessageOutput {
        val url = peopleServiceProperties.url + "/user/logout"

        val params = LinkedMultiValueMap<String, String>()
        params.add("username", username)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, MessageOutput::class.java)
    }

    fun registerByPeopleService(selectionInput: SelectionInput): MessageOutput {
        val url = peopleServiceProperties.url + "/user/register"

        val params = LinkedMultiValueMap<String, String>()
        params.add("username", selectionInput.userInput!!.username)
        params.add("password", selectionInput.userInput.password)
        params.add("email", selectionInput.userInput.email)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, MessageOutput::class.java)
    }

    fun updateUserByPeopleService(selectionInput: SelectionInput): MessageOutput {
        val url = peopleServiceProperties.url + "/user/update"

        val params = LinkedMultiValueMap<String, String>()
        params.add("username", selectionInput.userInput?.username)
        params.add("password", selectionInput.userInput?.password)
        params.add("icon", selectionInput.userInput?.icon)
        params.add("power", selectionInput.userInput?.power)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, MessageOutput::class.java)
    }
}