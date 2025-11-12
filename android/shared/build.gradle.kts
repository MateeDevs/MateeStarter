plugins {
    alias(libs.plugins.mateeStarter.android.library.compose)
}

android {
    namespace = "kmp.android.shared"
}

dependencies {

    implementation(project(":shared:umbrella"))
    implementation(libs.googlePlayServices.location)
}
