package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.type.CommitInput
import com.jianglianein.apigateway.model.type.CommitOutput
import com.jianglianein.apigateway.model.type.ResultOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class RemoteMessageService {
    @Autowired
    private lateinit var remoteServiceProperties: RemoteServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var httpClientService: HttpClientService

    fun getCommitByReceiver(receiver: String): MutableList<CommitOutput> {
        val url = remoteServiceProperties.messageServiceUrl + "/commit/getByReceiver"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("receiver", receiver)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, CommitOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun createCommit(commitInput: CommitInput): CommitOutput {
        val url = remoteServiceProperties.messageServiceUrl + "/commit/create"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("description", commitInput.description)
        params.add("announcer", commitInput.announcer)
        params.add("receiver", commitInput.receiver)
        params.add("cardId", commitInput.cardId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, CommitOutput::class.java)
    }

    fun updateCommit(commitInput: CommitInput): CommitOutput {
        val url = remoteServiceProperties.messageServiceUrl + "/commit/update"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("description", commitInput.description)
        params.add("isRead", commitInput.read)
        params.add("commitId", commitInput.id)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, CommitOutput::class.java)

    }

    fun removeCommit(commitId: String): ResultOutput {
        val url = remoteServiceProperties.messageServiceUrl + "/commit/remove"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("commitId", commitId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }
}