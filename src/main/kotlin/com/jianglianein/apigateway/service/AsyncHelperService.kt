package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
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
    fun selectAnnouncer(announcer: String): Future<String>{
        val peopleUrl = remoteServiceProperties.peopleServiceUrl + "/user/selectByName"
        val selectCardPosParams = LinkedMultiValueMap<String, Any>()
        selectCardPosParams.add("username", announcer)
        val resp = httpClientService.client(peopleUrl, HttpMethod.POST, selectCardPosParams)
        return AsyncResult<String>(resp)
    }

}