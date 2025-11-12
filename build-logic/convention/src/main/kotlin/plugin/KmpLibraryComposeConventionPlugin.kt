package plugin

import extensions.apply
import extensions.compose
import extensions.ktlintRuleset
import extensions.libs
import extensions.pluginManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
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
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                        implementation(compose.material)
                        implementation(compose.components.resources)
                        implementation(compose.components.uiToolingPreview)
                        ktlintRuleset(libs.ktlint.composeRules)
                    }
                }
            }
        }
    }
}