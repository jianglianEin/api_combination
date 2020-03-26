package com.jianglianein.apigateway.model.type

data class CardInput (val title: String? = null,
                      val description: String? = null,
                      val priority: String? = "lowest",
                      val storyPoints: Int? = null,
                      val processor: String? = null,
                      val founder: String? = null,
                      val status: String? = null,
                      val boardId: String? = null)
