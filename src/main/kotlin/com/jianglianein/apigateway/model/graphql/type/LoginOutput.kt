package com.jianglianein.apigateway.model.graphql.type

data class LoginOutput (val userOutput: UserOutput,
                        var token: String)