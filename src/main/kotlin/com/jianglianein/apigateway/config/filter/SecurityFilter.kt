package com.jianglianein.apigateway.config.filter

import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        response.setHeader("Strict-Transport-Security", "max-age=31536000")
        response.setHeader("Referrer-Policy", "no-referrer")
        response.setHeader("X-Content-Type-Options", "nosniff")
        response.setHeader("X-Frame-Options", "deny")
        response.setHeader("X-XSS-Protection", "1; mode=block")

        filterChain.doFilter(request, response)
    }
}