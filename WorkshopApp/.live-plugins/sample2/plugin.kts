import liveplugin.ActionGroupIds
import liveplugin.registerAction
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Constraints
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toNullableProperty
import com.intellij.ui.dsl.builder.whenItemSelectedFromUi
import liveplugin.implementation.common.Icons
import liveplugin.show
import liveplugin.virtualFile
import java.awt.event.ActionEvent
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.io.File
import javax.swing.JComponent

registerAction(
    id = "Custom Template",
    actionGroupId = ActionGroupIds.NewFileMenu,
    positionInGroup = Constraints.FIRST,
    action = GenerateAction()
)

class GenerateAction : AnAction(
    "Create Custom Template",
    null,
    AllIcons.Actions.ModuleDirectory,
) {
    override fun actionPerformed(event: AnActionEvent) {
        val projectPath = project?.basePath ?: return
        val clickedFolderPath = event.virtualFile?.path ?: return

        MyDialog(
            generateViewModel = { data ->
                val name = data.viewModelName
                val capitalizedName = name.replaceFirstChar { it.uppercaseChar() }
                val deCapitalizedName = name.replaceFirstChar { it.lowercaseChar() }
                val packageName = clickedFolderPath
                    .removePrefix(projectPath)
                    .lowercase()
                    .trim('/') //clean-up
                    .split('/')
                    .drop(4) // ignore app/src/main/java
                    .joinToString(".")
                val templateSourceFile = "$projectPath/.live-plugins/sample2/template/viewmodel_template"
                val outputFile = clickedFolderPath + "/" + data.viewModelName + ".kt"

                show("outputFile: $outputFile")
                show("templateSourceFile: $templateSourceFile")
                show("packageName: $packageName")
                val fileContent = File(templateSourceFile).readText()
                    .replace("%viewModelName%", deCapitalizedName)
                    .replace("%viewModelNameCapitalized%", capitalizedName)
                    .replace("%packageName%", packageName)
                File(outputFile).writeText(fileContent)
                // Signal IDE to refresh the file tree
                LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(outputFile))?.let {
                    VirtualFileManager.getInstance().syncRefresh()
                }
            }
        ).show()
    }


    class MyDialog(private val generateViewModel: (ViewModelData) -> Unit)
        : DialogWrapper(true) {

        lateinit var panel: DialogPanel
        private val data = ViewModelData(
            viewModelName = "MyViewModel",
        )

        init {
            title = "Create ViewModel"
            isResizable = false
            init()
        }

        override fun createDefaultActions() {
            super.createDefaultActions()
            myOKAction = object : DialogWrapperAction("Generate") {
                override fun doAction(event: ActionEvent) {
                    panel.apply()
                    pack()
                    panel.validateAll()
                    if (panel.isValid) {
                        generateViewModel(data)
                        close(CLOSE_EXIT_CODE)
                    }
                }
            }
            setOKButtonIcon(Icons.runPluginIcon)
        }

        override fun createCenterPanel(): JComponent {
            panel = panel {
                row("ViewModel name:") {
                    textField()
                        .bindText(data::viewModelName)
//                        .validationOnInput { input -> validateViewModelName(input.text) }
//                        .validationOnApply { input -> validateViewModelName(input.text) }
                }
            }
            return panel
        }

        private fun validateViewModelName(input: String): ValidationInfo? {
            return when {
                input.isBlank() -> ValidationInfo("ViewModel name cannot be empty")
                !input.matches(Regex("^[A-Za-z][A-Za-z0-9]*$")) -> ValidationInfo("ViewModel name must start with a letter and contain only alphanumeric characters")
                input.length < 3 -> ValidationInfo("ViewModel name must be at least 3 characters long")
                else -> null // No error
            }
        }

    }

}

data class ViewModelData(var viewModelName: String)
