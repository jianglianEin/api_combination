package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import org.springframework.stereotype.Component

@Component
class TeamAccessPermissionCheck: PermissionCheckInterface {

    override fun check(username: String, input: SelectionInput): Boolean {
        val commentInput = input.commitInput!!
        if (username == commentInput.receiver) {
            return true
        }
        return false
    }
}