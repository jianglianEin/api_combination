package com.jianglianein.apigateway.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class AuthCheckService {
    @Autowired
    private lateinit var asyncHelperService: AsyncHelperService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Cacheable("accessibleProject", key = "#username")
    fun getAccessibleResources(username: String): MutableList<String> {
        val typeRef = object : TypeReference<MutableMap<String, List<String>>>() {}

        val peopleServiceAccessibleResourcesFuture =
                asyncHelperService.getPeopleServiceAccessibleResources(username)
        val peopleServiceAccessibleResources: MutableMap<String, List<String>> =
                objectMapper.readValue(peopleServiceAccessibleResourcesFuture.get(), typeRef)

        val teams = peopleServiceAccessibleResources["teams"].toString().replace("[", "").replace("]", "")
        val projectServiceAccessibleResourcesFuture =
                asyncHelperService.getProjectServiceAccessibleResources(username, teams)
        val projectServiceAccessibleResources: MutableMap<String, List<String>> =
                objectMapper.readValue(projectServiceAccessibleResourcesFuture.get(), typeRef)

        return projectServiceAccessibleResources["projects"]!!.toMutableList()
    }

    fun permissionCheck(username: String, input: SelectionInput, methodName: String): Boolean {
        return PermissionCheckInterface.mappingToPermission(methodName).check(username, input)
    }

}