package com.jianglianein.apigateway.resource

import com.jianglianein.apigateway.config.EnvProperties
import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.enum.FunctionNameAuth1
import com.jianglianein.apigateway.model.enum.FunctionNameAuth2
import com.jianglianein.apigateway.model.type.ResultOutput
import com.jianglianein.apigateway.service.UploadService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.servlet.http.HttpServletRequest



@RestController
class ApiGateWayResource {
    @Autowired
    lateinit var env: EnvProperties
    @Autowired
    private lateinit var uploadService: UploadService

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
                  @RequestParam("username") username: String?,
                  @RequestParam("teamId") teamId: String?,
                  @RequestParam("projectId") projectId: String?,
                  @RequestParam("boardId") boardId: String?,
                  @RequestParam("cardId") cardId: String?,
                  @RequestParam("commentId") commentId: String?): ResultOutput{


        when{
            FunctionNameAuth0.values().map {
                it.toString()
            }.contains(functionName) -> {
                return ResultOutput(true, "Auth0 ok")
            }

            FunctionNameAuth1.values().map {
                it.toString()
            }.contains(functionName) -> {
                return ResultOutput(true, "Auth1 ok")
            }

            FunctionNameAuth2.values().map {
                it.toString()
            }.contains(functionName) -> {
                return ResultOutput(true, "Auth2 ok")
            }
        }

        return ResultOutput(false, "no function mapping")
    }
}