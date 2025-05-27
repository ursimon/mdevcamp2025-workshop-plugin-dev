package com.example.workshop.plugin.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.example.workshop.plugin.MyBundle
import com.example.workshop.plugin.services.MyProjectService
import javax.swing.JButton


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // TODO Task 6.2 - remove code and subscribe to Gradle sync events
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {

            // TODO Task 8.1

            // TODO Task 5.1 - remove code
            val label = JBLabel(MyBundle.message("randomLabel", "?"))

            add(label)
            add(JButton(MyBundle.message("shuffle")).apply {
                addActionListener {
                    label.text = MyBundle.message("randomLabel", service.getRandomNumber())
                }
            })

            // TODO Task 9.1 - remove code Grid with labels and combo boxes

            // TODO Task 9.3 - Initialize the table with labels and combo boxes

            // TODO Task 9.4 - Add a button to select the variants

            // TODO Task 9.5 - Add a button to reset the variants

            // TODO Task 9.6 - Add a buttons to the table

            // TODO Task 9.7 - Add the table to the main panel
        }

        // TODO Task 9.2 - remove code
    }
}
