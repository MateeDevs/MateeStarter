import extensions.libs

plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "kmp.shared.samplesharedviewmodel"
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
    commonMainImplementation(libs.molecule.runtime)
}
