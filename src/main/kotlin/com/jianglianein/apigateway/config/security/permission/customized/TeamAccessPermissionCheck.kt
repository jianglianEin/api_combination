package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.TeamTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TeamAccessPermissionCheck : PermissionCheckInterface {

    @Autowired
    private lateinit var teamTableRepository: TeamTableRepository

    override fun check(username: String, input: SelectionInput): Boolean {
        val teamId = input.teamInput?.id!!
        val accessibleTeams = teamTableRepository.selectAccessibleTeam(username)
        if (accessibleTeams != null && teamId in accessibleTeams) {
            return true
        }
        return false
    }
}