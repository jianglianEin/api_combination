package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class CardResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val cardInput = input.cardInput!!
        val cardIdCheck = accessibleResource["cards"]?.contains(cardInput.id)
        val boardIdCheck = accessibleResource["boards"]?.contains(cardInput.boardId)
        if (cardIdCheck != null && cardIdCheck) {
            return cardIdCheck
        } else if (boardIdCheck != null && boardIdCheck) {
            return boardIdCheck
        }
        return false
    }
}