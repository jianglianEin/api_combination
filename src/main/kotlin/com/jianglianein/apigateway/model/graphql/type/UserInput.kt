package com.jianglianein.apigateway.model.graphql.type

data class UserInput (val username: String? = null,
                      val password: String? = null,
                      val icon: String? = null,
                      val email: String? = null,
                      val power: String? = null)