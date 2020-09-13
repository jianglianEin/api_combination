package com.jianglianein.apigateway.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthCheckService {
    @Autowired
    private lateinit var asyncHelperService: AsyncHelperService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun getAccessibleResources(username: String): Map<String, List<String>> {
        val typeRef = object : TypeReference<MutableMap<String, List<String>>>() {}

        val peopleServiceAccessibleResourcesFuture =
                asyncHelperService.getPeopleServiceAccessibleResources(username)
        val messageServiceAccessibleResourcesFuture =
                asyncHelperService.getCommentServiceAccessibleResources(username)
        val peopleServiceAccessibleResources: MutableMap<String, List<String>> =
                objectMapper.readValue(peopleServiceAccessibleResourcesFuture.get(), typeRef)
        val messageServiceAccessibleResources: MutableMap<String, List<String>> =
                objectMapper.readValue(messageServiceAccessibleResourcesFuture.get(), typeRef)

        val teams = peopleServiceAccessibleResources["teams"].toString().replace("[", "").replace("]", "")
        val projectServiceAccessibleResourcesFuture =
                asyncHelperService.getProjectServiceAccessibleResources(username, teams)
        val projectServiceAccessibleResources: MutableMap<String, List<String>> =
                objectMapper.readValue(projectServiceAccessibleResourcesFuture.get(), typeRef)

        return peopleServiceAccessibleResources + projectServiceAccessibleResources + messageServiceAccessibleResources
    }

    fun permissionCheck(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput, methodName: String): Boolean {
        return PermissionCheckInterface.mappingToPermission(methodName).check(accessibleResource, input)
    }

}