package com.jianglianein.apigateway.model.type

data class CommitOutput (val id: String? = null,
                         val description: String? = null,
                         val announcer: String? = null,
                         val receiver: String? = null,
                         val updateTime: String? = null,
                         var cardId: String? = null,
                         val read: Boolean? = null)