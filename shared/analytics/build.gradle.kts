plugins {
    alias(libs.plugins.mateeStarter.kmp.library.core)
}

android {
    namespace = "kmp.shared.analytics"
}

dependencies {
    commonMainImplementation(project(":shared:base"))

    androidMainImplementation(libs.firebase.analytics)
}
