package com.jianglianein.apigateway.resource

import com.jianglianein.apigateway.config.EnvProperties
import com.jianglianein.apigateway.model.graphql.type.ResultOutput
import com.jianglianein.apigateway.service.UploadService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.web.bind.annotation.*
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
        val fileName = uploadService.checkAndReturnFileName(icon, request)
        val targetFile = File(path, fileName)
        uploadService.createFileAndReturnResult(icon, targetFile)
        val resource = FileSystemResource(targetFile)
        val url = uploadService.uploadImageToSmMsSite(resource)
        return ResultOutput(true, url)
    }

}