package com.jianglianein.apigateway.model.type

data class CommitType (val id: String? = null,
                       val description: String? = null,
                       val announcer: String? = null,
                       val receiver: String? = null,
                       val updateTime: String? = null,
                       val read: Boolean? = null,
                       val cardId: String? = null)