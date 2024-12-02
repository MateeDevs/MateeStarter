package config

import extensions.getBooleanProperty
import extensions.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

object KmmConfig {
    private fun includeX86(project: Project): Boolean =
        getBooleanProperty(project, "X86", false)

    private fun includeArm64(project: Project): Boolean =
        getBooleanProperty(project, "ARM64", true)

    private fun includeArm64Sim(project: Project): Boolean =
        getBooleanProperty(project, "ARM64SIM", true)

    fun getSupportedMobilePlatforms(extensions: KotlinMultiplatformExtension, project: Project): List<KotlinNativeTarget> =
        with(extensions) {
            val targets = mutableListOf<KotlinNativeTarget>()
            if (includeX86(project)) targets.add(iosX64())
            if (includeArm64(project)) targets.add(iosArm64())
            if (includeArm64Sim(project)) targets.add(iosSimulatorArm64())
            targets
        }

    fun getSupportedTvPlatforms(extensions: KotlinMultiplatformExtension, project: Project): List<KotlinNativeTarget> =
        with(extensions) {
            val targets = mutableListOf<KotlinNativeTarget>()
            if (includeX86(project)) targets.add(tvosX64())
            if (includeArm64(project)) targets.add(tvosArm64())
            if (includeArm64Sim(project)) targets.add(tvosSimulatorArm64())
            targets
        }
}

fun KotlinMultiplatformExtension.getIosTargets(project: Project, tvOSEnabled: Boolean = false): List<KotlinNativeTarget> =
    KmmConfig.getSupportedMobilePlatforms(this, project) +
        if (tvOSEnabled) {
            KmmConfig.getSupportedTvPlatforms(this, project)
        } else {
            emptyList()
        }

fun KotlinMultiplatformExtension.kmm(
    project: Project,
    nativeName: String,
    tvOSEnabled: Boolean = false,
) {
    with(project) {
        getIosTargets(project, tvOSEnabled).forEach {
            it.binaries.framework {
                baseName = nativeName
                isStatic = false
                export(libs.mokoResources)
                export(project(":shared:base"))
                export(project(":shared:sample"))
                export(project(":shared:samplesharedviewmodel"))
                export(project(":shared:samplecomposemultiplatform"))
                export(project(":shared:samplecomposenavigation"))
            }
        }
    }
}
