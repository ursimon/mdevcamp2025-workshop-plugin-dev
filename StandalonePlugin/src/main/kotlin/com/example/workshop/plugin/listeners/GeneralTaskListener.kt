package com.example.workshop.plugin.listeners

import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationEvent
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener
import org.jetbrains.plugins.gradle.util.GradleConstants

class GeneralTaskListener : ExternalSystemTaskNotificationListener {

    override fun onSuccess(projectPath: String, id: ExternalSystemTaskId) {
        if(id.isGradleSync()) {
            println("GeneralTask - Gradle sync onSuccess!")
        }
    }

    override fun onEnd(projectPath: String, id: ExternalSystemTaskId) {
        if(id.isGradleSync()) {
            println("GeneralTask - Gradle sync onEnd!")
        }
    }

    override fun onStatusChange(event: ExternalSystemTaskNotificationEvent) {
        if(event.isGradleSync()) {
            println("GeneralTask - Gradle sync onStatusChange!")
        }
    }

    private fun ExternalSystemTaskId.isGradleSync(): Boolean =
        projectSystemId == GradleConstants.SYSTEM_ID && type.name == ResolveProject

    private fun ExternalSystemTaskNotificationEvent.isGradleSync(): Boolean = id.isGradleSync()

    companion object {
        private const val ResolveProject = "RESOLVE_PROJECT"
    }
}