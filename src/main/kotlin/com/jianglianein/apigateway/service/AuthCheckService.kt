package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.enum.FunctionNameAuth1
import com.jianglianein.apigateway.model.enum.StatusCode
import com.jianglianein.apigateway.model.type.ProjectOutput
import com.jianglianein.apigateway.model.type.TeamOutPut
import com.jianglianein.apigateway.model.type.ResultRestOutput
import com.jianglianein.apigateway.poko.UserStatus
import com.jianglianein.apigateway.repository.FunctionStatusRepository
import com.jianglianein.apigateway.repository.UserStatusRepository
import com.microservice.peopleservice.poko.type.UserStatusType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    fun checkAuth1(functionName: String,
                   uid: String,
                   teamId: String?,
                   projectId: String?,
                   username: String?): ResultRestOutput {
        val userStatue = userStatusRepository.get(uid)
        if (userStatue.value != UserStatusType.Online) {

            return ResultRestOutput(false, StatusCode.NO_LOGIN.code.toString())
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
                    ResultRestOutput(false, StatusCode.NO_AUTH.code.toString())
                }else {
                    checkProjectId(checkProjects, projectId, uid, functionName)
                }
            }
            else -> {
                functionStatusRepository.update(uid, functionName)
                ResultRestOutput(true, StatusCode.SUCCESS.code.toString())
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
        return ResultRestOutput(false, StatusCode.NO_AUTH.code.toString())
    }

    private fun checkTeamId(checkTeams: MutableList<TeamOutPut>,
                            teamId: String,
                            uid: String,
                            functionName: String): ResultRestOutput {
        for (check in checkTeams) {
            if (teamId == check.id) {
                functionStatusRepository.update(uid, functionName)
                return ResultRestOutput(true, StatusCode.SUCCESS.code.toString())
            }
        }
        return ResultRestOutput(false, StatusCode.NO_AUTH.code.toString())
    }

    private fun checkProjectId(checkProject: MutableList<ProjectOutput>,
                               projectId: String,
                               uid: String,
                               functionName: String): ResultRestOutput {
        for (check in checkProject) {
            if (projectId == check.id) {
                functionStatusRepository.update(uid, functionName)
                return ResultRestOutput(true, StatusCode.SUCCESS.code.toString())
            }
        }
        return ResultRestOutput(false, StatusCode.NO_AUTH.code.toString())
    }
}