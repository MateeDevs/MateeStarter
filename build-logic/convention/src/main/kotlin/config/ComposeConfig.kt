package config

import com.android.build.api.dsl.CommonExtension
import extensions.androidTestImplementation
import extensions.apply
import extensions.implementation
import extensions.ktlintRuleset
import extensions.libs
import extensions.pluginManager
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeCompiler(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = with(commonExtension) {
    buildFeatures {
        compose = true
    }

    pluginManager {
        apply(libs.plugins.jetbrains.compose.compiler)
    }
}

internal fun Project.configureComposeDependencies() {
    dependencies {
        implementation(platform(libs.compose.bom))
        implementation(libs.androidX.core)
        implementation(libs.compose.ui)
        implementation(libs.compose.animation)
        implementation(libs.compose.foundation)
        implementation(libs.compose.material3)
        implementation(libs.compose.uiTooling)
        implementation(libs.activity.compose)
        implementation(libs.navigation3.runtime)
        implementation(libs.navigation3.ui)
        implementation(libs.lifecycle.viewModel.navigation3)
        implementation(libs.koin.android)
        implementation(libs.koin.androidx.compose)
        androidTestImplementation(libs.compose.uiTest)
    }
}

internal fun Project.configureComposeLint() {
    dependencies {
        ktlintRuleset(libs.ktlint.composeRules)
    }
}
