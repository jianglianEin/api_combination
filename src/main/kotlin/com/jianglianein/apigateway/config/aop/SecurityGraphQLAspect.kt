package com.jianglianein.apigateway.config.aop

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@Aspect
@Order(1)
class SecurityGraphQLAspect {
    private var logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var unsecuredHandler: UnsecuredHandler

    @Before("allGraphQLResolverMethods() && isDefinedInApplication() && !isMethodAnnotatedAsUnsecured()")
    fun doSecurityCheck() {
        logger.info { "doSecurityCheck" }

        if (SecurityContextHolder.getContext().authentication == null ||
                !SecurityContextHolder.getContext().authentication.isAuthenticated ||
                AnonymousAuthenticationToken::class.java.isAssignableFrom(SecurityContextHolder.getContext().authentication.javaClass)) {
            throw AccessDeniedException("User not authenticated")
        }
    }

    @Before("isMethodAnnotatedAsUnsecured()")
    fun doUnsecuredFunction() {
//        val currentRequestAttributes = RequestContextHolder.currentRequestAttributes()
//        val request = (currentRequestAttributes as ServletRequestAttributes).request
//        val response = (currentRequestAttributes as ServletRequestAttributes).response

        logger.info { "doUnsecuredFunction" }
    }

    @AfterReturning(returning="result", value= "isMethodAnnotatedAsUnsecured()")
    fun afterUnsecuredFunctionReturning(joinPoint: JoinPoint, result: Any): Any {
        logger.info { "afterUnsecuredFunctionReturning" }

        val methodName = joinPoint.signature.name
        unsecuredHandler.afterReturnHandle(methodName, result)

        return result
    }

    @Pointcut("within(com.jianglianein.apigateway.resolver.*)args(…)")
    fun allGraphQLResolverMethods() {
        logger.info { "allGraphQLResolverMethods" }
    }

    @Pointcut("within(com.jianglianein.apigateway..*)")
    fun isDefinedInApplication() {
        logger.info { "isDefinedInApplication" }
    }

    @Pointcut("@annotation(com.jianglianein.apigateway.config.security.Unsecured)")
    fun isMethodAnnotatedAsUnsecured() {
        logger.info { "isMethodAnnotatedAsUnsecured" }
    }
}