package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.model.graphql.type.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class RemotePeopleService {
    @Autowired
    private lateinit var remoteServiceProperties: RemoteServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var httpClientService: HttpClientService

    fun loginByPeopleService(selectionInput: SelectionInput): LoginOutput {
        val url = remoteServiceProperties.peopleServiceUrl + "/user/login"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("username", selectionInput.userInput?.username)
        params.add("password", selectionInput.userInput?.password)
        val resp = httpClientService.client(url, HttpMethod.POST, params)

        val loginOutput = LoginOutput(objectMapper.readValue(resp, UserOutput::class.java), "jwt")
        return loginOutput
    }

    @CacheEvict(value = ["accessibleProject"], key = "#selectionInput.userInput.username")
    fun logoutByPeopleService(selectionInput: SelectionInput): ResultOutput {

        return ResultOutput(true, "logout success")
    }

    fun registerByPeopleService(userInput: UserInput): ResultOutput {
        val url = remoteServiceProperties.peopleServiceUrl + "/user/register"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("username", userInput.username)
        params.add("password", userInput.password)
        params.add("email", userInput.email)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun updateUserByPeopleService(selectionInput: SelectionInput): UserOutput {
        val url = remoteServiceProperties.peopleServiceUrl + "/user/update"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("username", selectionInput.userInput?.username)
        params.add("password", selectionInput.userInput?.password)
        params.add("icon", selectionInput.userInput?.icon)
        params.add("power", selectionInput.userInput?.power)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, UserOutput::class.java)
    }


    fun createTeam(teamInput: TeamInput): TeamOutPut {
        val url = remoteServiceProperties.peopleServiceUrl + "/team/create"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("creator", teamInput.creator)
        params.add("teamname", teamInput.teamname)
        params.add("description", teamInput.description)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, TeamOutPut::class.java)
    }

    fun sendEmailToInviteReceiver(emailInput: EmailInput): ResultOutput {
        val url = remoteServiceProperties.peopleServiceUrl + "/mail/send"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("receiverMail", emailInput.receiverMail)
        params.add("receiver", emailInput.receiver)
        params.add("teamId", emailInput.teamId)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun selectUserBySubstring(usernameSubstring: String): MutableList<UserOutput> {
        val url = remoteServiceProperties.peopleServiceUrl + "/user/select"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("inputName", usernameSubstring)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, UserOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun updateTeam(teamInput: TeamInput): TeamOutPut {
        val url = remoteServiceProperties.peopleServiceUrl + "/team/update"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("id", teamInput.id)
        params.add("creator", teamInput.creator)
        params.add("teamname", teamInput.teamname)
        params.add("description", teamInput.description)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, TeamOutPut::class.java)
    }

    fun selectTeamByUsername(username: String): MutableList<TeamOutPut> {
        val url = remoteServiceProperties.peopleServiceUrl + "/team/selectTeamByUsername"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("username", username)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, TeamOutPut::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun selectPeopleByTeam(teamId: String): MutableList<UserOutput> {
        val url = remoteServiceProperties.peopleServiceUrl + "/user/selectByTeam"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("teamId", teamId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, UserOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun removeTeam(teamInput: TeamInput): ResultOutput? {
        val url = remoteServiceProperties.peopleServiceUrl + "/team/remove"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("id", teamInput.id)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }
}