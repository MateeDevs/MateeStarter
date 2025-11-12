package plugin

import config.kmm
import constants.ProjectConstants
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KmpFrameworkLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply<KmpLibraryConventionPlugin>()

            extensions.configure<KotlinMultiplatformExtension> {
                kmm(
                    project = project,
                    nativeName = ProjectConstants.iosShared,
                )
            }
        }
    }
}