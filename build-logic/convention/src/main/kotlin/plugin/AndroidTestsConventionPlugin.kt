package plugin

import extensions.android
import extensions.androidTestImplementation
import extensions.debugImplementation
import extensions.libs
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestsConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            android {
                defaultConfig {
                    testInstrumentationRunner = "kmp.android.util.TestRunner"
                }
            }

            dependencies {
                testImplementation(libs.junit)
                testImplementation(libs.konsist)

                debugImplementation(libs.koin.test)
                debugImplementation(platform(libs.compose.bom))
                androidTestImplementation(platform(libs.compose.bom))
            }
        }
    }
}