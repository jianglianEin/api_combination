package com.jianglianein.apigateway.config.security.permission.customized

import com.jianglianein.apigateway.config.security.permission.PermissionCheckInterface
import com.jianglianein.apigateway.model.graphql.SelectionInput
import com.jianglianein.apigateway.repository.ProjectTableRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Component

@Component
class ProjectAccessPermissionCheck : PermissionCheckInterface {

    @Autowired
    private lateinit var projectTableRepository: ProjectTableRepository

    override fun check(username: String, input: SelectionInput): Boolean {
        val projectInput = input.projectInput
        val boardInput = input.boardInput

        return when {
            projectInput == null && boardInput == null -> {
                false
            }
            projectInput == null && boardInput != null -> {
                checkProjectIsAccessible(username, boardInput.projectId!!)
            }
            projectInput != null && boardInput == null -> {
                checkProjectIsAccessible(username, projectInput.id!!)
            }
            else -> return false
        }
    }

    private fun checkProjectIsAccessible(username: String, projectId: String): Boolean {
        val accessibleProjects = projectTableRepository.selectAccessibleProject(username)
        if (projectId in accessibleProjects) return true

        return false
    }
}