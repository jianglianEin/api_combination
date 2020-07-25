package com.jianglianein.apigateway.model.enum

enum class JwtClaim(val claimName: String) {
    PROJECTS("projects"),
    BOARDS("boards"),
    CARDS("cards"),
    TEAMS("teams"),
    COMMENT("comment"),
    USERNAME("username"),
}