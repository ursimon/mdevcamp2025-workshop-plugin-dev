package com.example.workshop.plugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.example.workshop.plugin.MyBundle

@Service(Service.Level.PROJECT)
class MyProjectService(project: Project) {

    init {
        thisLogger().info(MyBundle.message("projectService", project.name))
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    // TODO Task 5.1 - remove code
    fun getRandomNumber() = (1..100).random()

    // TODO Task 7.6 - Get App Module of project

    // TODO Task 7.5 - Get all build variants of the project

    // TODO Task 7.2 - Get all Android modules of the project

    // TODO Task 7.3 - Get all dimensions from the Android modules

    // TODO Task 7.4 - Add build types to the dimensions
}
