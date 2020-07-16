package com.jianglianein.apigateway.config.security.iauthentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class IAuthentication : AbstractAuthenticationToken {
    private var principal: Any? = null
    private var credentials: Any? = null

    constructor(aPrincipal: Any?, aCredentials: Any?): super(null) {
        principal = aPrincipal
        credentials = aCredentials
    }

    constructor(aPrincipal: Any?, aCredentials: Any?,
                anAuthorities: Collection<GrantedAuthority?>?): super(anAuthorities) {
        principal = aPrincipal
        credentials = aCredentials
        isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return this.credentials
    }

    override fun getPrincipal(): Any? {
        return this.principal
    }

}
