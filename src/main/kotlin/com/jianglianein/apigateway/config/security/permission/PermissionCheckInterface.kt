package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.stereotype.Component

@Component
interface PermissionCheckInterface {

    fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean
    companion object {
        fun mappingToPermission(methodName: String): PermissionCheckInterface {
            return MethodMappingToPermission.mappingToPermission(methodName)
        }
    }
}