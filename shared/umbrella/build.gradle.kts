import co.touchlab.skie.configuration.DefaultArgumentInterop

plugins {
    alias(libs.plugins.mateeStarter.kmp.framework.library)
    alias(libs.plugins.skie)
}

skie {
    swiftBundling {
        enabled = true
    }

    features {
        group {
            DefaultArgumentInterop.Enabled(false)
        }
    }
}

android {
    namespace = "kmp.shared.umbrella"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.umbrella")
}

dependencies {
    commonMainApi(project(":shared:base"))
    commonMainApi(project(":shared:samplefeature"))

    commonMainImplementation(project(":shared:analytics"))
    commonMainImplementation(project(":shared:auth"))
}
