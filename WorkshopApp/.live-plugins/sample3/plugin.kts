import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.service
import com.intellij.openapi.observable.properties.GraphProperty
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JPanel
import com.intellij.openapi.wm.ToolWindowManager
import liveplugin.newDisposable
import liveplugin.registerParent
import liveplugin.registerProjectOpenListener
import liveplugin.runBackgroundTask
import liveplugin.runShellCommand
import java.awt.BorderLayout
import kotlin.io.path.absolutePathString

@Service(Service.Level.PROJECT)
@State(name = "LoginAutomatorState")
class LoginAutomatorStateComponent(private val project: Project) : SimplePersistentStateComponent<LoginAutomatorState>(LoginAutomatorState())

class LoginAutomatorState : BaseState() {
    var username by string()
    var password by string()
}

fun loginPanel(model: LoginDataModel) : JPanel {

    fun executeLoginScript(username: String, password: String) {
        runBackgroundTask("Running auto login", canBeCancelledInUI = false) { indicator ->
            indicator.isIndeterminate = true
            val scriptPath = "$pluginPath/scripts/login.sh".toNioPathOrNull()
            if (scriptPath == null || !scriptPath.toFile().exists()) {
                throw IllegalArgumentException("Script path is invalid or does not exist: $pluginPath/scripts/login.sh")
            }
            runShellCommand(
                scriptPath.absolutePathString(),
                username,
                password,
            )
        }
    }

    val panel = panel {
        row("Username:") {
            textField()
                .bindText(model.username)
                .focused()
        }
        row("Password:") {
            passwordField()
                .bindText(model.password)
        }
        row {
            button("Login") {
                executeLoginScript(
                    model.username.get(),
                    model.password.get()
                )
            }
        }
    }

    return JPanel(BorderLayout()).apply { add(panel, BorderLayout.CENTER) }
}

data class LoginDataModel(
    val stateComponent: LoginAutomatorStateComponent,
) {
    val username: GraphProperty<String> = PropertyGraph().property(stateComponent.state.username.orEmpty()).apply {
        afterChange { newValue ->
            stateComponent.state.username = newValue
            stateComponent.state.intIncrementModificationCount() // Signalize state change
        }
    }
    val password: GraphProperty<String> = PropertyGraph().property(stateComponent.state.password.orEmpty()).apply {
        afterChange { newValue ->
            stateComponent.state.password = newValue
            stateComponent.state.intIncrementModificationCount() // Signalize state change
        }
    }
}

class LoginToolWindowFactory(val model: LoginDataModel) : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val content = ApplicationManager.getApplication().getService(ContentFactory::class.java)
            .createContent(loginPanel(model), "Automator", false)
            .apply { setDisposer(pluginDisposable) }
        toolWindow.contentManager.addContent(content)
    }
}

registerProjectOpenListener(pluginDisposable) { project ->
    val stateComponent = project.service<LoginAutomatorStateComponent>()
    val model = LoginDataModel(stateComponent)
    val toolWindow = ToolWindowManager.getInstance(project)
        .registerToolWindow(
            "Login",
            builder = {
                anchor = ToolWindowAnchor.LEFT
                canCloseContent = false
                contentFactory = LoginToolWindowFactory(model)
            },
        )
    newDisposable(whenDisposed = {
        toolWindow.remove()
    }).registerParent(pluginDisposable)
}
