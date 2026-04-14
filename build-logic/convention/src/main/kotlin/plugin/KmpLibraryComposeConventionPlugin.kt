package plugin

import extensions.apply
import extensions.debugImplementation
import extensions.ktlintRuleset
import extensions.libs
import extensions.pluginManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KmpLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.jetbrains.compose.plugin)
                apply(libs.plugins.jetbrains.compose.compiler)
            }

            apply<KmpLibraryConventionPlugin>()

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.jetbrains.compose.runtime)
                        implementation(libs.jetbrains.compose.foundation)
                        implementation(libs.jetbrains.compose.material3)
                        implementation(libs.jetbrains.compose.uiUtil)
                        implementation(libs.jetbrains.compose.uiToolingPreview)
                        implementation(libs.mokoResources.compose)
                        implementation(libs.haze)
                        implementation(libs.haze.materials)
                        ktlintRuleset(libs.ktlint.composeRules)
                    }
                }
            }

            // Needed for Compose Previews to work in commonMain
            dependencies {
                debugImplementation(libs.jetbrains.compose.uiTooling)
            }
        }
    }
}
