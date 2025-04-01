package com.example.workshop.plugin.toolWindow

import com.android.tools.idea.gradle.project.sync.GRADLE_SYNC_TOPIC
import com.android.tools.idea.gradle.variant.view.BuildVariantUpdater
import com.example.workshop.plugin.entity.DimensionList
import com.example.workshop.plugin.listeners.MyGradleSyncListener
import com.example.workshop.plugin.services.MyProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import org.jetbrains.kotlin.idea.gradleTooling.capitalize
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel


class MyToolWindowFactory : ToolWindowFactory {

    init {}

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        project.messageBus.connect().subscribe(GRADLE_SYNC_TOPIC, MyGradleSyncListener(toolWindow))
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {
        private val project = toolWindow.project
        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {

            val buildVariants = service.getBuildVariants()
            // Print the dimensions and their flavors
            buildVariants.dimensions.forEach {
                println(it)
            }

            // Grid with labels and combo boxes
            val tablePanel = JPanel(GridLayout(buildVariants.dimensions.size + 1, 2))

            // Initialize the table with labels and combo boxes
            prepareUI(buildVariants, tablePanel)

            // Add a button to select the variants
            val btnSelect = JButton("Select").apply {
                addActionListener {
                    var selectedVariant = ""
                    tablePanel.components
                        .filterIsInstance<JComboBox<*>>()
                        .forEachIndexed { index, component ->
                            val selectedItem = component.selectedItem
                            if (selectedItem != null) {
                                selectedVariant += selectedItem.toString().let {
                                    if (index == 0) it.lowercase() else it.capitalize()
                                }
                            }
                    }
                    println("Selected variant: $selectedVariant")
                    service.getAppModule()?.let { appModule ->
                        BuildVariantUpdater.getInstance(project).updateSelectedBuildVariant(appModule, selectedVariant)
                    }
                }
            }

            // Add a button to reload the variants
            val btnReload = JButton("Reload").apply {
                addActionListener {
                    tablePanel.removeAll()
                    prepareUI(service.getBuildVariants(), tablePanel)
                    tablePanel.add(this)
                    tablePanel.add(btnSelect)
                }
            }
            // Add a buttons to the table
            tablePanel.add(btnReload)
            tablePanel.add(btnSelect)

            // Add the table to the main panel
            add(tablePanel)
        }


        /**
         * Prepares the UI by adding labels and combo boxes for each dimension and its flavors.
         *
         * @param buildVariants The list of dimensions and their flavors.
         * @param tablePanel The panel to which the labels and combo boxes will be added.
         */
        private fun prepareUI(buildVariants: DimensionList, tablePanel: JPanel) {
            buildVariants.dimensions.forEach { dimension ->
                val label = JBLabel(dimension.dimensionName.capitalize())
                val comboBox = JComboBox(dimension.flavors.map { it.title.capitalize() }.toTypedArray())
                comboBox.selectedItem = dimension.flavors.find { it.isSelected }?.title?.capitalize() ?: ""
                tablePanel.add(label)
                tablePanel.add(comboBox)
            }
        }}
}
