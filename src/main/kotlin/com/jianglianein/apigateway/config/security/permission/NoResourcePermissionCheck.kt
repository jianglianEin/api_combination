package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.stereotype.Component

@Component
class NoResourcePermissionCheck: PermissionCheckInterface {

    override fun check(username: String, input: SelectionInput): Boolean {
        return true
    }
}