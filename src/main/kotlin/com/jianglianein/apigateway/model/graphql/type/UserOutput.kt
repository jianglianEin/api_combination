package com.jianglianein.apigateway.model.graphql.type

data class UserOutput (val id: String? = null,
                       val username: String? = null,
                       val password: String? = null,
                       val icon: String? = null,
                       val email: String? = null,
                       val power: String? = null)