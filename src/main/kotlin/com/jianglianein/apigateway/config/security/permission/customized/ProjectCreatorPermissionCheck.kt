package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.stereotype.Component

@Component
class ProjectCreatorPermissionCheck: PermissionCheckInterface {

    override fun check(username: String, input: SelectionInput): Boolean {
        val creator = input.projectInput?.creator!!
        if (username == creator) {
            return true
        }
        return false
    }
}