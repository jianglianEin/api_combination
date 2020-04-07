package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.type.ProjectOutput
import com.jianglianein.apigateway.model.type.TeamOutPut
import com.jianglianein.apigateway.poko.UserStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheService {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService
    @Autowired
    private lateinit var remoteScrumProjectService: RemoteScrumProjectService

    @Cacheable("teamsCheck", key = "#userStatue.updateBy")
    fun getTeamsEvidence(userStatue: UserStatus): MutableList<TeamOutPut> {
        val checkTeams = remotePeopleService.selectTeamByUsername(userStatue.updateBy)

        return checkTeams
}

    @Cacheable("projectsCheck", key = "#userStatue.updateBy")
    fun getProjectsEvidence(userStatue: UserStatus): MutableList<ProjectOutput> {
        val checkProjects = remoteScrumProjectService.selectProjectsByCreator(userStatue.updateBy).toMutableList()

        return checkProjects
    }
}