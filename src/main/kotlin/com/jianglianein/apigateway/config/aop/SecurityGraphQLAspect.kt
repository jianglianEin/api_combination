package com.jianglianein.apigateway.config.aop

import com.jianglianein.apigateway.config.security.SecurityHandler
import com.jianglianein.apigateway.config.security.UnsecuredHandler
import com.jianglianein.apigateway.model.graphql.SelectionInput
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
@Aspect
@Order(1)
class SecurityGraphQLAspect {
    private var logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var unsecuredHandler: UnsecuredHandler

    @Autowired
    private lateinit var securityHandler: SecurityHandler

    @Before("allGraphQLResolverMethods() && isDefinedInApplication() && !isMethodAnnotatedAsUnsecured()")
    fun doSecurityCheck(joinPoint: JoinPoint) {
        logger.info { "doSecurityCheck" }
        val input = joinPoint.args[0] as SelectionInput
        val methodName = (joinPoint.signature as MethodSignature).method.name

        val currentRequestAttributes = RequestContextHolder.currentRequestAttributes()
        val request = (currentRequestAttributes as ServletRequestAttributes).request
//        val response = (currentRequestAttributes as ServletRequestAttributes).response
        val authorization = request.getHeader("Authorization")
        val token = authorization?.replace("Bearer ", "")

        if (token.isNullOrEmpty()) {
            throw AccessDeniedException("User not authenticated")
        } else if (!securityHandler.authCheck(token, input, methodName)) {
            throw AccessDeniedException("User not authenticated")
        }
    }

    @Before("isMethodAnnotatedAsUnsecured()")
    fun doUnsecuredFunction() {

        logger.info { "doUnsecuredFunction" }
    }

    @AfterReturning(returning = "result", value = "isMethodAnnotatedAsUnsecured()")
    fun afterUnsecuredFunctionReturning(joinPoint: JoinPoint, result: Any): Any {
        logger.info { "afterUnsecuredFunctionReturning" }

        val methodName = joinPoint.signature.name
        unsecuredHandler.afterReturnHandle(methodName, result)

        return result
    }

    @AfterReturning(returning = "result", value = "allGraphQLResolverMethods()")
    fun afterSecurityCheck(joinPoint: JoinPoint, result: Any) {
        logger.info { "afterSecurityCheck" }

        val currentRequestAttributes = RequestContextHolder.currentRequestAttributes()
        val request = (currentRequestAttributes as ServletRequestAttributes).request
        val authorization = request.getHeader("Authorization")
        val token = authorization.replace("Bearer ", "")

        val methodName = joinPoint.signature.name
        securityHandler.afterReturnHandle(methodName, token)
    }

    @Pointcut("within(com.jianglianein.apigateway.resolver.*)args(…)")
    fun allGraphQLResolverMethods() {
        logger.info { "allGraphQLResolverMethods" }
    }

    @Pointcut("within(com.jianglianein.apigateway.resource.*)args(…)")
    fun allRestfulResolverMethods() {
        logger.info { "allRestfulResolverMethods" }
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