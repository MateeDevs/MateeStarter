package plugin

import com.android.build.api.dsl.LibraryExtension
import config.configureBuildVariants
import config.configureKotlinAndroid
import config.configureTests
import config.getIosTargets
import extensions.apply
import extensions.ktlint
import extensions.libs
import extensions.pluginManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.android.library)
                apply(libs.plugins.kotlin.multiplatform)
                apply(libs.plugins.serialization)
                apply(libs.plugins.ktlint)
                apply(libs.plugins.mokoResources)
            }

            apply<KotlinConventionPlugin>()

            ktlint {
                filter {
                    exclude { entry ->
                        val path = entry.file.absolutePath
                        path.contains("/generated/") || path.contains("build/generated")
                    }
                }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                targets.configureEach {
                    compilations.configureEach {
                        compileTaskProvider.get().compilerOptions {
                            freeCompilerArgs.add("-Xexpect-actual-classes")
                        }
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                compileSdk = libs.versions.sdk.compile.get().toInt()

                defaultConfig {
                    minSdk = libs.versions.sdk.min.get().toInt()
                }

                configureBuildVariants()

                buildFeatures {
                    buildConfig = true
                }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                getIosTargets(project)

                sourceSets {
                    commonMain.dependencies {
                        api(libs.mokoResources)
                        implementation(libs.coroutines.core)
                        implementation(libs.atomicFu)
                        implementation(libs.dateTime)
                        implementation(libs.koin.core)
                        implementation(libs.koin.core.viewModel)
                        implementation(libs.koin.compose)
                        implementation(libs.koin.compose.viewModel)
                        implementation(libs.bundles.settings)
                        implementation(libs.bundles.ktor.common)
                        implementation(libs.kermit)
                        implementation(libs.ktor.auth)
                    }

                    androidMain.dependencies {
                        implementation(libs.ktor.android)
                        implementation(libs.lifecycle.viewModel)
                    }

                    iosMain.dependencies {
                        implementation(libs.ktor.ios)
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureTests()
            }
        }
    }
}
