plugins {
    alias(libs.plugins.mateeStarter.android.application.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "kmp.android"
}

dependencies {
    implementation(project(":shared:umbrella"))
    implementation(project(":android:shared"))
    implementation(project(":android:samplefeature"))
}
