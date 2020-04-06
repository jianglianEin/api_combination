package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.enum.FunctionNameAuth0
import com.jianglianein.apigateway.model.type.ProjectOutput
import com.jianglianein.apigateway.model.type.TeamOutPut
import com.jianglianein.apigateway.model.type.ResultRestOutput
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
    private lateinit var remotePeopleService: RemotePeopleService
    @Autowired
    private lateinit var remoteScrumProjectService: RemoteScrumProjectService

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
                   projectId: String?): ResultRestOutput {
        val userStatue = userStatusRepository.get(uid)
        if (userStatue.value != UserStatusType.Online) {

            return ResultRestOutput(false, "Auth1 failed")
        }

        val checkTeams = remotePeopleService.selectTeamByUsername(userStatue.updateBy)
        val checkProjects = remoteScrumProjectService.selectProjectsByCreator(userStatue.updateBy).toMutableList()

        return when{
            teamId != null -> {
                checkTeamId(checkTeams, teamId, uid, functionName)
            }
            projectId != null -> {
                checkProjectId(checkProjects, projectId, uid, functionName)
            }
            else -> {
                functionStatusRepository.update(uid, functionName)
                ResultRestOutput(true, "Auth1 ok")
            }
        }
    }

    private fun checkTeamId(checkTeams: MutableList<TeamOutPut>,
                            teamId: String?,
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
                               projectId: String?,
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