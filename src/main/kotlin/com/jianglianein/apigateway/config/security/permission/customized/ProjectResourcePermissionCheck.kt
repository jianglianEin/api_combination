package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class ProjectResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val projectInput = input.projectInput!!
        val projectIdCheck = accessibleResource["projects"]?.contains(projectInput.id)
        if (projectIdCheck != null && projectIdCheck) {
            return projectIdCheck
        }
        return false
    }
}