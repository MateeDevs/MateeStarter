package plugin

import extensions.apply
import extensions.implementation
import extensions.java
import extensions.libs
import extensions.pluginManager
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("unused")
class KotlinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.kotlin.android)
            }

            val versionCode = libs.versions.java.get().toInt()
            val javaVersion = JavaVersion.toVersion(versionCode)

            java {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(versionCode))
                }
            }

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = javaVersion.toString()
                    freeCompilerArgs = freeCompilerArgs + listOf(
                        "-Xallow-jvm-ir-dependencies",
                        "-opt-in=kotlin.RequiresOptIn",
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
