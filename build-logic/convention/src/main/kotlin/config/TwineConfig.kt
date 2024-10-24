package config

import org.gradle.api.Project
import org.gradle.internal.os.OperatingSystem
import java.io.File

fun Project.configureTwine() {
    tasks.register("generateTwine") {
        Twine.generateAllStringFiles(
            project = project,
            twineFile = "${rootProject.file("twine").absolutePath}/strings.txt",
            targetPath = "${project.rootDir.absolutePath}/shared/base/src/commonMain/moko-resources",
            targetFileName = "strings.xml",
            languages = listOf("sk", "en", "cs"),
            baseLanguage = "en",
        )
    }
}

private object Twine {

    fun generateAllStringFiles(
        project: Project,
        twineFile: String,
        targetPath: String,
        targetFileName: String,
        languages: List<String>,
        baseLanguage: String,
    ) {
        val scripts = languages.map { language ->
            val path = "$targetPath/${if (language == baseLanguage) "base" else language}"
            val file = "$path/$targetFileName"

            File(path).mkdirs()
            File(file).createNewFile()

            "twine generate-localization-file $twineFile $file -f android --lang $language"
        }
        scripts.forEach { script ->
            project.exec {
                // Add twine into path
                // This should be also refactored
                val twinePath = project.findProperty("twinePath")
                if (twinePath != null) {
                    environment["PATH"] =
                        "${environment["PATH"]}${File.pathSeparator}$twinePath"
                }

                if (OperatingSystem.current().isMacOsX || OperatingSystem.current().isLinux) {
                    commandLine("sh", "-c", script)
                } else if (OperatingSystem.current().isWindows) {
                    commandLine("cmd", "/c", script)
                }
            }
        }
    }
}
