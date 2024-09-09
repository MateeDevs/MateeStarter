plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
}

android {
    namespace = "kmp.shared.sample"
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    commonMainImplementation(project(":shared:base"))
}
