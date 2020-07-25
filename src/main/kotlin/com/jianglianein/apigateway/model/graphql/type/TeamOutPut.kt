package com.jianglianein.apigateway.model.graphql.type

import java.io.Serializable

data class TeamOutPut (val id: String? = null,
                       val creator: String? = null,
                       val teamname: String? = null,
                       val description: String? = null): Serializable