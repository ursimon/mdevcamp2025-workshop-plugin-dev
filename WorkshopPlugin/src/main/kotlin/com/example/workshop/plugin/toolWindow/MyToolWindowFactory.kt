package com.example.workshop.plugin.toolWindow

import com.android.tools.idea.gradle.project.sync.GRADLE_SYNC_TOPIC
import com.android.tools.idea.gradle.variant.view.BuildVariantUpdater
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.example.workshop.plugin.MyBundle
import com.example.workshop.plugin.entities.DimensionList
import com.example.workshop.plugin.listeners.MyGradleSyncListener
import com.example.workshop.plugin.services.MyProjectService
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // TODO Task 5.2 - subscribe to Gradle sync events
        //project.messageBus.connect().subscribe(GRADLE_SYNC_TOPIC, MyGradleSyncListener(toolWindow))
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val project = toolWindow.project
        private val service = project.service<MyProjectService>()

        // TODO Task 7.1
//        private val buildVariants = service.getBuildVariants()


        fun getContent() = JBPanel<JBPanel<*>>().apply {

            // TODO Task 7.1
//            buildVariants.dimensions.forEach{
//                println(it)
//            }

            // TODO Task 8.1 - remove code Grid with labels and combo boxes
//            val tablePanel = JPanel(GridLayout(buildVariants.dimensions.size + 1, 2))

            // TODO Task 8.3 - Initialize the table with labels and combo boxes
//            prepareUI(buildVariants, tablePanel)

            // TODO Task 8.4 - Add a button to select the variants
//            val btnSelect = JButton("Select").apply {
//                addActionListener {
//                    var selectedVariant = ""
//                    tablePanel.components
//                        .filterIsInstance<JComboBox<*>>()
//                        .forEachIndexed { index, component ->
//                            val selectedItem = component.selectedItem
//                            if (selectedItem != null) {
//                                selectedVariant += selectedItem.toString().let {
//                                    if (index == 0) it.lowercase() else it.capitalize()
//                                }
//                            }
//                        }
//                    println("Selected variant: $selectedVariant")
//                    service.getAppModule()?.let { appModule ->
//                        BuildVariantUpdater.getInstance(project).updateSelectedBuildVariant(appModule, selectedVariant)
//                    }
//                }
//            }
            // TODO Task 8.5 - Add a button to reset the variants
//            val btnReload = JButton("Reload").apply {
//                addActionListener {
//                    tablePanel.removeAll()
//                    prepareUI(service.getBuildVariants(), tablePanel)
//                    tablePanel.add(this)
//                    tablePanel.add(btnSelect)
//                }
//            }
            // TODO Task 8.6 - Add a buttons to the table
//            tablePanel.add(btnReload)
//            tablePanel.add(btnSelect)
            // TODO Task 8.7 - Add the table to the main panel
//            add(tablePanel)
        }

        // TODO Task 8.2 - Prepares the UI
//        private fun prepareUI(buildVariants: DimensionList, tablePanel: JPanel) {
//            buildVariants.dimensions.forEach { dimension ->
//                val label = JBLabel(dimension.dimensionName.capitalize())
//                val comboBox = JComboBox(dimension.flavors.map { it.title.capitalize() }.toTypedArray())
//                comboBox.selectedItem = dimension.flavors.find { it.isSelected }?.title?.capitalize() ?: ""
//                tablePanel.add(label)
//                tablePanel.add(comboBox)
//            }
//        }
    }
}
