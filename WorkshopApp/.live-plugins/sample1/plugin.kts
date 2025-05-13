import liveplugin.show

// To run a plugin press the "Run plugin" button in the "Plugins" tool window ("alt+C, alt+E" or "ctrl/cmd+shift+L" shortcut).
// This will run the plugin currently selected in the tool window or opened in the editor.
// You might notice that it takes a few seconds the first time you run a Kotlin plugin.
// However, it should get faster on the following runs.

// The code below will show a balloon message with "Hello world" text (which should also appear in "Event Log" tool window).
// If there is no balloon, it might be disabled in "IDE Settings - Notifications".
show("Hello mDevCamp 2025")

// There are several implicit variables available in plugin.kts files.
//  - "isIdeStartup" which is true on IDE start, otherwise false. Plugins are executed on IDE start
//    if "Plugins tool window -> Settings -> Run Plugins on IDE Start" option is enabled.
//  - "project" in which the plugin is executed, it can be null on IDE start or if there are no open projects.
//    It is an instance of com.intellij.openapi.project.Project, see
//    https://upsource.jetbrains.com/idea-ce/file/idea-ce-ba0c8fc9ab9bf23a71a6a963cd84fc89b09b9fc8/platform/core-api/src/com/intellij/openapi/project/Project.java
//  - "pluginDisposable" instance of com.intellij.openapi.Disposable which will be disposed
//    when you press the "Unload Plugin" button in the "Plugins" tool window or just before the same plugin is run again.
//    It can be useful, for example, to pass into listeners so that when you re-evaluate plugin code, the old listeners are removed.
//    See also https://plugins.jetbrains.com/docs/intellij/disposers.html#implementing-the-disposable-interface
//  - "pluginPath" with an absolute path to this plugin's folder normalised to use "/" as path separator.

//show("project: $project")
//show("pluginPath: $pluginPath")
