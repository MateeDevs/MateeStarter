package plugin

import com.android.build.api.dsl.LibraryExtension
import config.KmmConfig
import config.configureBuildVariants
import config.configureKotlinAndroid
import config.configureTests
import extensions.apply
import extensions.java
import extensions.libs
import extensions.pluginManager
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KmmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.android.library)
                apply(libs.plugins.kotlin.multiplatform)
                apply(libs.plugins.serialization)
                apply(libs.plugins.ktlint)
                apply(libs.plugins.mokoResources)
            }

            val versionCode = libs.versions.java.get().toInt()
            java {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(versionCode))
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

                sourceSets {
                    val commonMain by getting {
                        dependencies {
                            api(libs.mokoResources)
                            implementation(libs.coroutines.core)
                            implementation(libs.atomicFu)
                            implementation(libs.dateTime)
                            implementation(libs.koin.core)
                            implementation(libs.bundles.settings)
                            implementation(libs.bundles.ktor.common)
                            implementation(libs.kermit)
                        }
                    }

                    val androidMain by getting {
                        dependsOn(commonMain)
                        dependencies {
                            implementation(libs.ktor.android)
                            implementation(libs.lifecycle.viewModel)
                        }
                    }

                    val iosMain by creating {
                        dependsOn(commonMain)
                        dependencies {
                            implementation(libs.ktor.ios)
                        }
                    }
                    KmmConfig.getSupportedMobilePlatforms(this@configure, project).forEach {
                        with(KmmConfig) {
                            getByName(it.asMainSourceSetName).dependsOn(iosMain)
                        }
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
