package com.jianglianein.apigateway.resource

import com.jianglianein.apigateway.config.EnvProperties
import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.enum.FunctionNameAuth1
import com.jianglianein.apigateway.model.type.ResultOutput
import com.jianglianein.apigateway.model.type.ResultRestOutput
import com.jianglianein.apigateway.service.AuthCheckService
import com.jianglianein.apigateway.service.UploadService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest
import java.io.File
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class ApiGateWayResource {
    @Autowired
    lateinit var env: EnvProperties
    @Autowired
    private lateinit var uploadService: UploadService
    @Autowired
    private lateinit var authCheckService: AuthCheckService

    private var logger = KotlinLogging.logger {}

    @GetMapping()
    fun hello(): String {
        logger.info { "run in PeopleService" }
        logger.info { env.env }
        return "hello world\n" + env.env
    }

    @PostMapping("/api/uploadImage")
    fun uploadImage(@RequestParam("icon") icon: MultipartFile, request: HttpServletRequest): ResultOutput? {
        val path = uploadService.checkAndReturnFilePath(request)
        val fileName = uploadService.checkAndReturnFileName(icon)
        val targetFile = File(path, fileName)
        return uploadService.createFileAndReturnResult(icon, targetFile)
    }

    @PostMapping("/api/checkAuth")
    fun checkAuth(@RequestParam("functionName") functionName: String,
                  @RequestParam("teamId") teamId: String?,
                  @RequestParam("projectId") projectId: String?,
                  response: HttpServletResponse,
                  request: HttpServletRequest): ResultRestOutput {

        return when {
            FunctionNameAuth0.values().map {
                it.functionName
            }.contains(functionName) -> {
                authCheckService.checkAuth0(functionName, response)
            }

            FunctionNameAuth1.values().map {
                it.functionName
            }.contains(functionName) -> {
                val authentication = request.getHeader("Authorization")
                val uid = authentication?.replace("Bearer ", "")
                authCheckService.checkAuth1(functionName, uid!!, teamId, projectId)
            }
            else -> ResultRestOutput(false, "no function mapping")
        }
    }
}