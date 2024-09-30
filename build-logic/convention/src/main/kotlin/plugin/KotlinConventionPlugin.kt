package plugin

import extensions.implementation
import extensions.kotlin
import extensions.libs
import extensions.pluginManager
import extensions.apply
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("unused")
class KotlinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.kotlin.android)
            }

            val javaVersionCode = libs.versions.java.get().toInt()

            kotlin {
                jvmToolchain(javaVersionCode)
            }

            tasks.withType<KotlinCompile> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.fromTarget(javaVersionCode.toString()))
                    freeCompilerArgs.addAll(
                        "-Xallow-jvm-ir-dependencies",
                        "-opt-in=kotlin.RequiresOptIn",
                        "-Xexpect-actual-classes",
                    )
                }
            }

            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.immutableCollections)
            }
        }
    }
}
