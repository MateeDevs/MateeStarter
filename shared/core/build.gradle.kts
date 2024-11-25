import co.touchlab.skie.configuration.DefaultArgumentInterop
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

plugins {
    alias(libs.plugins.mateeStarter.kmm.xcframework.library)
    alias(libs.plugins.skie)
    alias(libs.plugins.google.ksp)
}

skie {
    swiftBundling {
        enabled = true
    }

    features {
        group {
            DefaultArgumentInterop.Enabled(false)
        }
    }
}

android {
    namespace = "kmp.shared.core"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.core")
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    commonMainApi(project(":shared:base"))
    commonMainApi(project(":shared:sample"))
    commonMainApi(project(":shared:samplesharedviewmodel"))
    commonMainApi(project(":shared:samplecomposemultiplatform"))
    commonMainApi(project(":shared:samplecomposenavigation"))
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
