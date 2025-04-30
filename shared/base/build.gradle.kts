plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
}

android {
    namespace = "kmp.shared.base"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.base")
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    androidMainImplementation(libs.firebase.analytics)
}
