package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.type.CommitOutput
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
}