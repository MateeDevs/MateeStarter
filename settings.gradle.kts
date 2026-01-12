pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "MateeStarter"

include(":android:app")
include(":android:shared")
include(":android:samplefeature")

include(":shared:umbrella")
include(":shared:base")
include(":shared:analytics")
include(":shared:samplefeature")
include(":shared:auth")
