plugins {
    alias(libs.plugins.mateeStarter.kmm.xcframework.library)
    alias(libs.plugins.touchlabSkie)
}

android {
    namespace = "kmp.shared.core"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.core")
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    commonMainApi(project(":shared:base"))
    commonMainApi(project(":shared:sample"))
    commonMainApi(project(":shared:samplesharedviewmodel"))
    commonMainApi(project(":shared:samplecomposemultiplatform"))
}
