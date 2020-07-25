package com.jianglianein.apigateway.model.graphql.type

data class AuthCheckInput (val username: String? = null,
                           val teamId: String? = null,
                           val projectId: String? = null,
                           val boardId: String? = null,
                           val cardId: String? = null,
                           val commentId: String? = null)