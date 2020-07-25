package com.jianglianein.apigateway.model.graphql.type

data class CardInput (val id: String? = null,
                      val title: String? = null,
                      val description: String? = null,
                      val priority: String? = null,
                      val storyPoints: Int? = null,
                      val processor: String? = null,
                      val founder: String? = null,
                      val status: String? = null,
                      val boardId: String? = null)
