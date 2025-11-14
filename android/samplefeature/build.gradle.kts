plugins {
    alias(libs.plugins.mateeStarter.android.library.compose)
}

android {
    namespace = "kmp.android.samplefeature"
}

dependencies {
    implementation(project(":android:shared"))
    implementation(project(":shared:umbrella"))
}
