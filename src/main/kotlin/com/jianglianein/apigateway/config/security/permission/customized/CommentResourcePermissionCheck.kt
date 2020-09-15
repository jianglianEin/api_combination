package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput

class CommentResourcePermissionCheck : PermissionCheckInterface {
    override fun check(accessibleResource: MutableMap<String, List<String>>, input: SelectionInput): Boolean {
        val commentInput = input.commitInput!!
        val receiverCheck = accessibleResource["username"]?.contains(commentInput.receiver)
        val cardIdCheck = accessibleResource["cards"]?.contains(commentInput.cardId)
        val commentIdCheck = accessibleResource["comment"]?.contains(commentInput.id)
        return when {
            receiverCheck != null && receiverCheck -> receiverCheck
            cardIdCheck != null && cardIdCheck -> cardIdCheck
            commentIdCheck != null && commentIdCheck -> commentIdCheck
            else -> false
        }
    }
}