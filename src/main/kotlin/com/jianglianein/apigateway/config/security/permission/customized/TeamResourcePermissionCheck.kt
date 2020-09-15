package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class TeamResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val teamInput = input.teamInput!!
        val teamIdCheck = accessibleResource["teams"]?.contains(teamInput.id)
        if (teamIdCheck != null && teamIdCheck) {
            return teamIdCheck
        }
        return false
    }
}