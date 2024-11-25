import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.skie)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "kmp.shared.samplecomposemultiplatform"
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    commonMainImplementation(project(":shared:base"))
    commonMainImplementation(project(":shared:sample"))
    commonMainImplementation(project(":shared:samplesharedviewmodel"))

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(compose.components.uiToolingPreview)
    ktlintRuleset(libs.ktlint.composeRules)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composeSwiftBridge)
        }
    }
}

dependencies {
    val composeSwiftBridgeKsp = libs.composeSwiftBridge.ksp
    "kspCommonMainMetadata"(composeSwiftBridgeKsp)
    "kspIosSimulatorArm64"(composeSwiftBridgeKsp)
    "kspIosArm64"(composeSwiftBridgeKsp)
    "kspIosX64"(composeSwiftBridgeKsp)
    "kspAndroid"(composeSwiftBridgeKsp)
    skieSubPlugin(libs.composeSwiftBridge.skie)
}

tasks.withType<com.google.devtools.ksp.gradle.KspTaskNative>().configureEach {
    options.add(SubpluginOption("apoption", "compose-swift-bridge.targetName=$target"))
}

ksp {
    // Configure the module with a custom default factory name, the default is called "NativeView"
    arg("compose-swift-bridge.defaultFactoryName", "SampleComposeMultiplatformView")
}

// support for generating ksp code in commonCode
// see https://github.com/google/ksp/issues/567
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    // this fixes Ktlint failing because it tries to operate on the output of KSP task
    if (gradle.startParameter.taskNames.none { it.contains("ktlint") }) {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}
