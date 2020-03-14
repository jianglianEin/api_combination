package com.jianglianein.apigateway.resource

import com.jianglianein.apigateway.config.EnvProperties
import com.jianglianein.apigateway.model.type.ResultOutput
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
class ApiGateWayResource {
    @Autowired
    lateinit var env: EnvProperties

    private var logger = KotlinLogging.logger {}

    @GetMapping()
    fun hello(): String {
        logger.info { "run in PeopleService" }
        logger.info { env.env }
        return "hello world\n" + env.env
    }

    @PostMapping("/api/uploadImage")
    fun uploadImage(@RequestParam("icon") icon: MultipartFile, request: HttpServletRequest): ResultOutput? {
        val path = checkAndReturnFilePath(request)
        val fileName = checkAndReturnFileName(icon)
        val targetFile = File(path, fileName)
        return createFileAndReturnResult(icon, targetFile)
    }

    private fun checkAndReturnFilePath(request: HttpServletRequest): String {
        val path: String = request.session.servletContext.getRealPath("img/icon")
        val filePath = File(path)
        println("file path: $path")
        if (!filePath.exists() && !filePath.isDirectory) {
            println("file path not exit, create file path: $filePath")
            filePath.mkdirs()
        }
        return path
    }

    private fun checkAndReturnFileName(icon: MultipartFile): String {
        val originalFileName = icon.originalFilename
        val type = originalFileName!!.substring(originalFileName.lastIndexOf(".") + 1)
        val name = originalFileName.substring(0, originalFileName.lastIndexOf("."))

        val d = Date()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val date = sdf.format(d)
        return "$date$name.$type"
    }

    private fun createFileAndReturnResult(icon: MultipartFile, targetFile: File): ResultOutput { //在指定路径下创建一个文件
        return try {
            icon.transferTo(targetFile)
            ResultOutput(true, "upload success")
        } catch (e: IOException) {
            e.printStackTrace()
            ResultOutput(false, "upload failed")
        }
    }

}