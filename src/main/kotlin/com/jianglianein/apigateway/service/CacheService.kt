package com.jianglianein.apigateway.service

import com.jianglianein.apigateway.model.graphql.type.ProjectOutput
import com.jianglianein.apigateway.model.graphql.type.TeamOutPut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheService {
    @Autowired
    private lateinit var remotePeopleService: RemotePeopleService
    @Autowired
    private lateinit var remoteScrumProjectService: RemoteScrumProjectService

    @Cacheable("teamsCheck", key = "#username")
    fun getTeamsEvidence(username: String): MutableList<TeamOutPut> {
        val checkTeams = remotePeopleService.selectTeamByUsername(username)

        return checkTeams
}

    @Cacheable("projectsCheck", key = "#username")
    fun getProjectsEvidence(username: String): MutableList<ProjectOutput> {
        val checkProjects = remoteScrumProjectService.selectProjectsByCreator(username).toMutableList()

        return checkProjects
    }
}