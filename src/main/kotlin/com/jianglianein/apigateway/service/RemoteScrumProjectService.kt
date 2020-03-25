package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.type.*
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

        val params = LinkedMultiValueMap<String, Any>()
        params.add("creator", creator)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, ProjectOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun createProject(projectInput: ProjectInput): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/create"
        val params = initProjectCreateParams(projectInput)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun updateProject(projectInput: ProjectInput): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/update"
        val params = initProjectUpdateParams(projectInput)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    private fun initProjectCreateParams(projectInput: ProjectInput): LinkedMultiValueMap<String, Any> {
        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectName", projectInput.projectName)
        params.add("creator", projectInput.creator)
        params.add("rowTitle", projectInput.rowTitle)
        params.add("iteration", projectInput.iteration)

        if (projectInput.teamId == null) {
            params.add("teamId", "None")
        } else {
            params.add("teamId", projectInput.teamId)
        }

        if (projectInput.colTitle == null) {
            params.add("colTitle", "block, confirm, dev, test, sign off")
        } else {
            params.add("colTitle", projectInput.colTitle)
        }

        return params
    }

    private fun initProjectUpdateParams(projectInput: ProjectInput): LinkedMultiValueMap<String, Any> {
        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectId", projectInput.id)
        params.add("projectName", projectInput.projectName)
        params.add("rowTitle", projectInput.rowTitle)
        params.add("iteration", projectInput.iteration)
        params.add("teamId", projectInput.teamId)
        params.add("colTitle", projectInput.colTitle)
        return params
    }

    fun removeProject(projectId: String): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/remove"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectId", projectId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun createBoard(boardInput: BoardInput): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/board/create"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectId", boardInput.projectId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun removeBoard(boardId: String): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/board/remove"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("boardId", boardId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }

    fun selectBoardsByProjectId(projectId: String): MutableList<BoardOutput> {
        val url = remoteServiceProperties.projectServiceUrl + "/board/selectByProject"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectId", projectId)

        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, BoardOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }
}