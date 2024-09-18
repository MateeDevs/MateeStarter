package plugin

import extensions.android
import extensions.androidTestImplementation
import extensions.debugImplementation
import extensions.libs
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestsConventionPlugin : Plugin<Project> {

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

                debugImplementation(libs.androidx.uiautomator)
                debugImplementation(libs.koin.test)
                debugImplementation(platform(libs.compose.bom))
//                debugImplementation(libs.compose.test.manifest)
                androidTestImplementation(platform(libs.compose.bom))
//                androidTestImplementation(libs.compose.test.runner)
                androidTestImplementation(libs.espresso.core)
            }
        }
    }
}