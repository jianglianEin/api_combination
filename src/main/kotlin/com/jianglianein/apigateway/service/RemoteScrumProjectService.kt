package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.type.ProjectOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class RemoteScrumProjectService {
    @Autowired
    private lateinit var remoteServiceProperties: RemoteServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    @Autowired
    private lateinit var httpClientService: HttpClientService

    fun selectProjectsByCreator(creator: String): MutableList<ProjectOutput> {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/selectByCreator"

        val params = LinkedMultiValueMap<String, String>()
        params.add("creator", creator)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, ProjectOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }
}