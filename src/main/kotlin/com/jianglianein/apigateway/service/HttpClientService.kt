package com.jianglianein.apigateway.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class HttpClientService {
    @Autowired
    private lateinit var client: RestTemplate

    fun client(url: String, method: HttpMethod, params: LinkedMultiValueMap<String, Any>): String? {
        val headers = HttpHeaders()

        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val requestEntity = HttpEntity(params, headers)

        val response = client.exchange(url, method, requestEntity, String::class.java)
        return response.body
    }
}