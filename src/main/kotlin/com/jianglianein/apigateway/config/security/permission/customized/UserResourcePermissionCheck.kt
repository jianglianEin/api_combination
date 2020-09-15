package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class UserResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val userInput = input.userInput!!
        val usernameCheck = accessibleResource["username"]?.contains(userInput.username)
        if (usernameCheck != null && usernameCheck) {
            return usernameCheck
        }
        return false
    }
}