plugins {
    alias(libs.plugins.mateeStarter.android.library.compose)
}

android {
    namespace = "kmp.android.sample"
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":android:shared"))
}
