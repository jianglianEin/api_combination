package com.jianglianein.apigateway.model.type

data class ProjectInput (val id: String? = null,
                         val projectName: String? = null,
                         val creator: String? = null,
                         val teamId: String? = null,
                         val colTitle: ArrayList<String>? = null,
                         val rowTitle: ArrayList<String>? = null,
                         val iteration: Int? = 14)