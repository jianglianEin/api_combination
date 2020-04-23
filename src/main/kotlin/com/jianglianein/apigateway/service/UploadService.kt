package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.type.ResultOutput
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class UploadService{
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
}