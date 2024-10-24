plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "kmp.shared.samplecomposenavigation"
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
//    commonMainImplementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
//    commonMainImplementation("org.jetbrains.compose.material:material-navigation:1.7.0-beta02")
    ktlintRuleset(libs.ktlint.composeRules)
}
