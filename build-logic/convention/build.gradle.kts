plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.ktlint)
}

group = "buildlogic"

java {
    val versionCode = libs.versions.java.get().toInt()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(versionCode))
    }
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(libs.androidTools.gradle)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        plugin(
            dependency = libs.plugins.mateeStarter.android.application.compose,
            pluginName = "AndroidApplicationComposeConventionPlugin",
        )
    }
    plugins {
        plugin(
            dependency = libs.plugins.mateeStarter.android.application.core,
            pluginName = "AndroidApplicationConventionPlugin",
        )
        plugin(
            dependency = libs.plugins.mateeStarter.android.library.compose,
            pluginName = "AndroidLibraryComposeConventionPlugin",
        )
        plugin(
            dependency = libs.plugins.mateeStarter.android.library.core,
            pluginName = "AndroidLibraryConventionPlugin",
        )
        plugin(
            dependency = libs.plugins.mateeStarter.kmm.library,
            pluginName = "KmmLibraryConventionPlugin",
        )
        plugin(
            dependency = libs.plugins.mateeStarter.kmm.xcframework.library,
            pluginName = "KmmXCFrameworkLibraryConventionPlugin",
        )
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.plugin(
    dependency: Provider<out PluginDependency>,
    pluginName: String,
) {
    val pluginId = dependency.get().pluginId
    register(pluginId) {
        id = pluginId
        implementationClass = "plugin.$pluginName"
    }
}
