package com.example.workshop.plugin.services

import com.android.tools.idea.gradle.project.model.GradleAndroidModel
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.example.workshop.plugin.MyBundle
import com.example.workshop.plugin.entities.Dimension
import com.example.workshop.plugin.entities.DimensionList
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager

@Service(Service.Level.PROJECT)
class MyProjectService(val project: Project) {

    init {
        thisLogger().info(MyBundle.message("projectService", project.name))
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    // TODO Task 6.6 - Get App Module of project
//    fun getAppModule(): Module? = ModuleManager
//        .getInstance(project)
//        .modules.firstOrNull { it.name.contains("app") }

    // TODO Task 6.5 - Get all build variants of the project
//    fun getBuildVariants()  = with(getAndroidModules()) {
//        getDimensions().also { dims ->
//            dims.createOrderedDimensionMaps(this)
//            dims.addBuildTypes(this)
//            dims.selectFrom(this)
//            dims.deselectDuplicates()
//        }
//    }

    // TODO Task 6.2 - Get all Android modules of the project
//    private fun getAndroidModules() =
//        ModuleManager.getInstance(project)
//            .modules
//            .map { GradleAndroidModel.get(it) }
//            .filter { it?.moduleName != null }
//            .map { it!! }
//            .distinct()


    // TODO Task 6.3 - Get all dimensions from the Android modules
//    private fun List<GradleAndroidModel>.getDimensions(): DimensionList {
//        val dimensionList = DimensionList()
//        for (module in this) {
//            module.androidProject.multiVariantData?.productFlavors?.forEach { flavorObj ->
//                flavorObj.productFlavor.dimension?.let { dim ->
//                    dimensionList.getOrCreateDimension(dim).addUniqueVariant(flavorObj.productFlavor.name)
//                }
//            }
//        }
//        return dimensionList
//    }

    // TODO Task 6.4 - Add build types to the dimensions
//    private fun DimensionList.addBuildTypes(modules: List<GradleAndroidModel>) {
//        val buildTypeDimension = Dimension(DimensionList.BUILD_TYPE_NAME)
//        modules.getBuildTypes().forEach { buildTypeDimension.addUniqueVariant(it) }
//        this.dimensions.add(buildTypeDimension)
//    }
//
//    private fun List<GradleAndroidModel>.getBuildTypes(): List<String> =
//        this.flatMap { it.buildTypeNames }.distinct()
}
