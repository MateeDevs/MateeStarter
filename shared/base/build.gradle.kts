import com.sentiary.config.Format

plugins {
    alias(libs.plugins.mateeStarter.kmp.library.compose)
    alias(libs.plugins.mateeStarter.kmp.sentiary)
}

android {
    namespace = "kmp.shared.base"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.base")
}

ktlint {
    filter {
        exclude("**/KeychainAccessibleAfterFirstUnlockSettings.kt")
    }
}

dependencies {
    commonMainImplementation(libs.molecule.runtime)
}

tasks.named("prepareComposeResourcesTaskForCommonMain") {
    dependsOn("sentiaryUpdateLocalizations")
}

sentiary {
    exportPaths {
        fun String.dropSubtag() = split("-").first()

        create("Android") {
            format.set(Format.Android)
            outputDirectory.set(layout.projectDirectory.dir("src/commonMain/moko-resources"))

            folderNamingStrategy { language, isDefault ->
                if (isDefault) {
                    "base"
                } else {
                    language.dropSubtag()
                }
            }
        }

        create("iOS") {
            format.set(Format.Apple)
            outputDirectory.set(rootProject.layout.projectDirectory.dir("ios/PresentationLayer/Sources/PresentationLayer/Sources/PresentationLayer/Resources/Localizable"))

            folderNamingStrategy { language, isDefault ->
                if (isDefault) {
                    "Base.lproj"
                } else {
                    "${language.dropSubtag()}.lproj"
                }
            }
        }
    }
}
