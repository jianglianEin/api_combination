package com.jianglianein.apigateway.config.filter

import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class CorsFilter : OncePerRequestFilter() {


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token")
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token")

        filterChain.doFilter(request, response)
    }
}