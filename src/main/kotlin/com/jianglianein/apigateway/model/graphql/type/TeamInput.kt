package com.jianglianein.apigateway.model.graphql.type

data class TeamInput (val id: String? = null,
                      val creator: String? = null,
                      val teamname: String? = null,
                      val description: String? = null)