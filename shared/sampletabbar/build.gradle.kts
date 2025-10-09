plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "kmp.shared.sampletabbar"
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
    commonMainImplementation(project(":shared:samplecomposemultiplatform"))

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(compose.components.uiToolingPreview)
    ktlintRuleset(libs.ktlint.composeRules)

    commonMainImplementation(libs.coil)
    androidMainImplementation(libs.coil.okhttp)
    iosMainImplementation(libs.coil.ktor)
    commonMainImplementation(libs.haze)
    commonMainImplementation(libs.haze.materials)
}
