package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.OtherServiceProperties
import com.jianglianein.apigateway.model.type.ResultOutput
import com.jianglianein.apigateway.poko.SmPictureBedResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class UploadService {
    @Autowired
    private lateinit var httpClientService: HttpClientService
    @Autowired
    private lateinit var otherServiceProperties: OtherServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun checkAndReturnFilePath(request: HttpServletRequest): String {
        val path: String = request.session.servletContext.getRealPath("img/icon")
        val filePath = File(path)
        println("file path: $path")
        if (!filePath.exists() && !filePath.isDirectory) {
            println("file path not exit, create file path: $filePath")
            filePath.mkdirs()
        }
        return path
    }

    fun checkAndReturnFileName(icon: MultipartFile, request: HttpServletRequest): String {
        val originalFileName = icon.originalFilename
        val contentType: String = (request as AbstractMultipartHttpServletRequest).multiFileMap["icon"]?.get(0)?.contentType!!
        val imageType = contentType.split("/").last()

        val d = Date()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val date = sdf.format(d)
        return "$date$originalFileName.$imageType"
    }

    fun createFileAndReturnResult(icon: MultipartFile, targetFile: File): ResultOutput { //在指定路径下创建一个文件
        return try {
            icon.transferTo(targetFile)
            ResultOutput(true, targetFile.name)
        } catch (e: IOException) {
            e.printStackTrace()
            ResultOutput(false, "upload failed")
        }
    }

    fun uploadImageToSmMsSite(icon: FileSystemResource): String {
        val params = LinkedMultiValueMap<String, Any>()
        val url = otherServiceProperties.pictureBedUrl + "upload"
        val token = otherServiceProperties.pictureBedToken
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        headers["Authorization"] = token
        headers["User-Agent"] =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
        headers["Accept"] = "*/*"

        params.add("smfile", icon)
        params.add("format", "json ")

        val resp = httpClientService.client(url, HttpMethod.POST, params, headers)
        val smResult = objectMapper.readValue(resp, SmPictureBedResult::class.java)
        return smResult.data!!.url!!
    }
}