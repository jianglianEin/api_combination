package com.jianglianein.apigateway.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthCheckService {
    @Autowired
    private lateinit var asyncHelperService: AsyncHelperService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun getJwtClaim(username: String): Map<String, List<String>> {
        val typeRef = object : TypeReference<MutableMap<String, List<String>>>() {}

        val peopleServiceClaimFuture = asyncHelperService.getPeopleServiceClaim(username)
        val messageServiceClaimFuture = asyncHelperService.getCommentServiceClaim(username)
        val peopleServiceClaim: MutableMap<String, List<String>> = objectMapper.readValue(peopleServiceClaimFuture.get(), typeRef)
        val messageServiceClaim: MutableMap<String, List<String>> = objectMapper.readValue(messageServiceClaimFuture.get(), typeRef)

        val teams = peopleServiceClaim["teams"].toString().replace("[", "").replace("]", "")
        val projectServiceClaimFuture = asyncHelperService.getProjectServiceClaim(username, teams)
        val projectServiceClaim: MutableMap<String, List<String>> = objectMapper.readValue(projectServiceClaimFuture.get(), typeRef)

        return peopleServiceClaim + projectServiceClaim + messageServiceClaim
    }

}