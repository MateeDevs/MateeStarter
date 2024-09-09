plugins {
    alias(libs.plugins.mateeStarter.android.library.compose)
}

android {
    namespace = "kmp.android.shared"
}

dependencies {

    implementation(project(":shared:core"))
    implementation(libs.googlePlayServices.location)
}
