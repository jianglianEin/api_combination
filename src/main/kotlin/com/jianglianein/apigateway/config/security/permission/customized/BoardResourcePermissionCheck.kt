package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class BoardResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val boardInput = input.boardInput!!
        val boardIdCheck = accessibleResource["boards"]?.contains(boardInput.id)
        val projectIdCheck = accessibleResource["projects"]?.contains(boardInput.projectId)

        if (boardIdCheck != null && boardIdCheck) {
            return boardIdCheck
        } else if (projectIdCheck != null && projectIdCheck) {
            return projectIdCheck
        }
        return false
    }
}