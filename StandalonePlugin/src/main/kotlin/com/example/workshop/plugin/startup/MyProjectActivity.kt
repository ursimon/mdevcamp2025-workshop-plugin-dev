package com.example.workshop.plugin.startup

import com.android.tools.idea.gradle.project.sync.GradleSyncState
import com.example.workshop.plugin.listeners.GeneralTaskListener
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener.EP_NAME
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class MyProjectActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        EP_NAME.point.registerExtension(GeneralTaskListener(), project)

        val gradleSyncState = GradleSyncState.getInstance(project)

        if (gradleSyncState.isSyncInProgress) {
            println("Gradle synchronizace je právě v průběhu.")
        } else if (gradleSyncState.isSyncNeeded().toBoolean()) {
            println("Gradle synchronizace je potřeba.")
        } else {
            println("Gradle synchronizace je dokončena.")
        }
    }
}