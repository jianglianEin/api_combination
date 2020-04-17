package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.enum.FunctionNameAuth1
import com.jianglianein.apigateway.model.type.ProjectOutput
import com.jianglianein.apigateway.model.type.TeamOutPut
import com.jianglianein.apigateway.model.type.ResultRestOutput
import com.jianglianein.apigateway.poko.UserStatus
import com.jianglianein.apigateway.repository.FunctionStatusRepository
import com.jianglianein.apigateway.repository.UserStatusRepository
import com.microservice.peopleservice.poko.type.UserStatusType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class AuthCheckService {
    @Autowired
    private lateinit var toolService: ToolService
    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository
    @Autowired
    private lateinit var functionStatusRepository: FunctionStatusRepository
    @Autowired
    private lateinit var cacheService: CacheService

    fun checkAuth0(functionName: String, response: HttpServletResponse): ResultRestOutput {
        if (functionName == FunctionNameAuth0.LOGIN.functionName){
            val time = System.currentTimeMillis().toString()
            val uid = toolService.encode(time + functionName + ThreadLocalRandom.current())
            functionStatusRepository.update(uid, functionName)

            val cookie = Cookie("uid",uid)
            cookie.maxAge = 12 * 3600
            response.addCookie(cookie)
            return ResultRestOutput(true, "Auth0 ok", uid)
        }
        return ResultRestOutput(true, "Auth0 ok")
    }

    fun checkAuth1(functionName: String,
                   uid: String,
                   teamId: String?,
                   projectId: String?,
                   username: String?): ResultRestOutput {
        val userStatue = userStatusRepository.get(uid)
        if (userStatue.value != UserStatusType.Online) {

            return ResultRestOutput(false, "Auth1 failed")
        }

        val  checkProjects = cacheService.getProjectsEvidence(userStatue)
        val  checkTeams = cacheService.getTeamsEvidence(userStatue)

        return when{
            username != null && projectId != null -> {
                checkComment(checkProjects, projectId, uid, username, userStatue, functionName)
            }
            teamId != null -> {
                checkTeamId(checkTeams, teamId, uid, functionName)
            }
            projectId != null -> {
                if (FunctionNameAuth1.isCommentFunction(functionName)){
                    ResultRestOutput(false, "Auth1 failed")
                }else {
                    checkProjectId(checkProjects, projectId, uid, functionName)
                }
            }
            else -> {
                functionStatusRepository.update(uid, functionName)
                ResultRestOutput(true, "Auth1 ok")
            }
        }
    }

    private fun checkComment(checkProject: MutableList<ProjectOutput>,
                             projectId: String,
                             uid: String,
                             username: String,
                             userStatue: UserStatus,
                             functionName: String): ResultRestOutput {
        if (userStatue.updateBy == username){
            return checkProjectId(checkProject, projectId, uid, functionName)
        }
        return ResultRestOutput(false, "Auth1 failed")
    }

    private fun checkTeamId(checkTeams: MutableList<TeamOutPut>,
                            teamId: String,
                            uid: String,
                            functionName: String): ResultRestOutput {
        for (check in checkTeams) {
            if (teamId == check.id) {
                functionStatusRepository.update(uid, functionName)
                return ResultRestOutput(true, "Auth1 ok")
            }
        }
        return ResultRestOutput(false, "Auth1 failed")
    }

    private fun checkProjectId(checkProject: MutableList<ProjectOutput>,
                               projectId: String,
                               uid: String,
                               functionName: String): ResultRestOutput {
        for (check in checkProject) {
            if (projectId == check.id) {
                functionStatusRepository.update(uid, functionName)
                return ResultRestOutput(true, "Auth1 ok")
            }
        }
        return ResultRestOutput(false, "Auth1 failed")
    }
}