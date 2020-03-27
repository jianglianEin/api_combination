package com.jianglianein.apigateway.model.type

data class CommitInput (val description: String? = null,
                        val announcer: String? = null,
                        val receiver: String? = null,
                        var cardId: String? = null)