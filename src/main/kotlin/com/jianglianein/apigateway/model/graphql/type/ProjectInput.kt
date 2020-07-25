package com.jianglianein.apigateway.model.graphql.type

data class ProjectInput (val id: String? = null,
                         val projectName: String? = null,
                         val creator: String? = null,
                         val teamId: String? = null,
                         val colTitle: String? = null,
                         val rowTitle: String? = null,
                         val iteration: Int? = 14)