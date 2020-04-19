package com.jianglianein.apigateway.model.type

data class CardOutput (val id: String? = null,
                       val createTime: String? = null,
                       val title: String? = null,
                       val description: String? = null,
                       val priority: String? = null,
                       val storyPoints: Int? = null,
                       val processor: String? = null,
                       val founder: String? = null,
                       val status: String? = null,
                       val number: Int? = null)
