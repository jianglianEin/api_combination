package com.jianglianein.apigateway.config.security.permission

import com.jianglianein.apigateway.model.graphql.SelectionInput

interface PermissionCheckInterface {
    fun check(username: String, input: SelectionInput): Boolean
}