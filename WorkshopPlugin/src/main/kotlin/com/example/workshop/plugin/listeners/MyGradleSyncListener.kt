package com.example.workshop.plugin.listeners

import com.android.tools.idea.gradle.project.sync.GradleSyncListenerWithRoot
import com.example.workshop.plugin.toolWindow.MyToolWindowFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import org.jetbrains.annotations.SystemIndependent

class MyGradleSyncListener(private val toolWindow: ToolWindow) : GradleSyncListenerWithRoot {

//    override fun syncStarted(project: Project, rootProjectPath: @SystemIndependent String) {
//        println("Gradle sync started")
//    }
//
//    override fun syncSkipped(project: Project) {
//        println("Gradle sync skipped")
//        createMyToolWindow()
//    }
//
//    override fun syncSucceeded(project: Project, rootProjectPath: @SystemIndependent String) {
//        println("Gradle sync succeeded")
//        createMyToolWindow()
//    }
//
//    override fun syncFailed(project: Project, errorMessage: String, rootProjectPath: @SystemIndependent String) {
//        println("Gradle sync failed")
//    }
//
//    private fun createMyToolWindow() {
//        val myToolWindow = MyToolWindowFactory.MyToolWindow(toolWindow)
//        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
//        toolWindow.contentManager.removeAllContents(true)
//        toolWindow.contentManager.addContent(content)
//    }
}