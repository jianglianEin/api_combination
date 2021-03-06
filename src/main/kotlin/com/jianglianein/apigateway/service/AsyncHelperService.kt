package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.graphql.type.TeamOutPut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import java.util.concurrent.Future

@Service
class AsyncHelperService {
    @Autowired
    private lateinit var remoteServiceProperties: RemoteServiceProperties
    @Autowired
    private lateinit var httpClientService: HttpClientService

    @Async
    fun selectCardPosAsync(cardId: String): Future<String>{
        val projectUrl = remoteServiceProperties.projectServiceUrl + "/card/selectCardPosById"
        val selectCardPosParams = LinkedMultiValueMap<String, Any>()
        selectCardPosParams.add("cardId", cardId)
        val resp = httpClientService.client(projectUrl, HttpMethod.POST, selectCardPosParams)
        return AsyncResult<String>(resp)
    }

    @Async
    fun selectAnnouncerAsync(announcer: String): Future<String>{
        val peopleUrl = remoteServiceProperties.peopleServiceUrl + "/user/selectByName"
        val selectCardPosParams = LinkedMultiValueMap<String, Any>()
        selectCardPosParams.add("username", announcer)
        val resp = httpClientService.client(peopleUrl, HttpMethod.POST, selectCardPosParams)
        return AsyncResult<String>(resp)
    }

    @Async
    fun selectProjectByTeamAsync(it: TeamOutPut, projectUrl: String): Future<String> {
        val selectByTeamParams = LinkedMultiValueMap<String, Any>()
        selectByTeamParams.add("teamId", it.id)
        val projectJoinByTeamResp = httpClientService.client(projectUrl, HttpMethod.POST, selectByTeamParams)
        return AsyncResult<String>(projectJoinByTeamResp)
    }

    @Async
    fun getPeopleServiceAccessibleResources(username: String): Future<String> {
        val permissionUrl = remoteServiceProperties.peopleServiceUrl + "/auth/permission"
        val peoplePermissionParams = LinkedMultiValueMap<String, Any>()
        peoplePermissionParams.add("username", username)
        val resp = httpClientService.client(permissionUrl, HttpMethod.POST, peoplePermissionParams)
        return AsyncResult<String>(resp)
    }

    @Async
    fun getProjectServiceAccessibleResources(username: String, teams: String): Future<String> {
        val permissionUrl = remoteServiceProperties.projectServiceUrl + "/auth/permission"
        val projectPermissionParams = LinkedMultiValueMap<String, Any>()
        projectPermissionParams.add("username", username)
        projectPermissionParams.add("teams", teams)
        val resp = httpClientService.client(permissionUrl, HttpMethod.POST, projectPermissionParams)
        return AsyncResult<String>(resp)
    }

//    @Async
//    fun getCommentServiceAccessibleResources(username: String): Future<String> {
//        val authClaimUrl = remoteServiceProperties.messageServiceUrl + "/auth/permission"
//        val commentClaimParams = LinkedMultiValueMap<String, Any>()
//        commentClaimParams.add("username", username)
//        val resp = httpClientService.client(authClaimUrl, HttpMethod.POST, commentClaimParams)
//        return AsyncResult<String>(resp)
//    }

}