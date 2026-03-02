package plugin

import com.sentiary.SentiaryPluginExtension
import extensions.apply
import extensions.libs
import extensions.pluginManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke

class SentiaryConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager {
                apply(libs.plugins.sentiary)
            }

            extensions.configure<SentiaryPluginExtension> {
                defaultLanguage.set("en-GB")
            }
        }
    }
}