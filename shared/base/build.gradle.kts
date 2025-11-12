plugins {
    alias(libs.plugins.mateeStarter.kmp.library.compose)
}

android {
    namespace = "kmp.shared.base"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.base")
}

dependencies {
    androidMainImplementation(libs.firebase.analytics)
}
