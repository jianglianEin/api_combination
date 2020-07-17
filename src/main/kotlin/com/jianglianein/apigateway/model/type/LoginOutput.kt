package com.jianglianein.apigateway.model.type

data class LoginOutput (val userOutput: UserOutput,
                        var token: String)