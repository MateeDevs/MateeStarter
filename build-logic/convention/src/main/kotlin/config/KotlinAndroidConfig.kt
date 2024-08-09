package config

import com.android.build.api.dsl.CommonExtension
import extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = with(commonExtension) {
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }

    val versionCode = libs.versions.java.get().toInt()
    val javaVersion = JavaVersion.toVersion(versionCode)
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
