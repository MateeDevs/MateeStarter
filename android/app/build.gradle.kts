plugins {
    alias(libs.plugins.mateeStarter.android.application.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "kmp.android"
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":android:shared"))
    implementation(project(":android:sample"))
    implementation(project(":android:samplesharedviewmodel"))
    implementation(project(":android:samplecomposemultiplatform"))
}
