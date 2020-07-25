package com.jianglianein.apigateway.model.graphql.type

data class CommitInput (val id: String? = null,
                        val description: String? = null,
                        val announcer: String? = null,
                        val receiver: String? = null,
                        val cardId: String? = null,
                        val read: Boolean? = false)