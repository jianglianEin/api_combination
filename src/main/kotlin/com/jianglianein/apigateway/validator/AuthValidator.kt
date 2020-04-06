package com.jianglianein.apigateway.validator

import com.jianglianein.apigateway.repository.FunctionStatusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthValidator {
    @Autowired
    private lateinit var functionStatusRepository: FunctionStatusRepository

    fun checkFunctionAuth(uid: String, functionName: String): Boolean {
        return functionStatusRepository.get(uid, functionName)
    }

}