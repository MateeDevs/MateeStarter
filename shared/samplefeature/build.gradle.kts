plugins {
    alias(libs.plugins.mateeStarter.kmp.library.compose)
}

android {
    namespace = "kmp.shared.samplefeature"
}

dependencies {
    commonMainImplementation(project(":shared:base"))
    commonMainImplementation(project(":shared:analytics"))
}
