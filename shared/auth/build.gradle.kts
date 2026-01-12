plugins {
    alias(libs.plugins.mateeStarter.kmp.library.core)
}

android {
    namespace = "kmp.shared.auth"
}

dependencies {
    commonMainImplementation(project(":shared:base"))
}
