package extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependencyNotation: Any) =
    add(ExtensionConstants.IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.implementation(dependencies: List<Any>) =
    dependencies.forEach(::implementation)

fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) =
    add(ExtensionConstants.TEST_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any) =
    add(ExtensionConstants.ANDROID_TEST_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any) =
    add(ExtensionConstants.DEBUG_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.alphaImplementation(dependencyNotation: Any) =
    add(ExtensionConstants.ALPHA_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.betaImplementation(dependencyNotation: Any) =
    add(ExtensionConstants.BETA_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.api(dependencyNotation: Any) =
    add(ExtensionConstants.API, dependencyNotation)

fun DependencyHandlerScope.ksp(dependencyNotation: Any) =
    add(ExtensionConstants.KSP, dependencyNotation)

fun DependencyHandlerScope.kapt(dependencyNotation: Any) =
    add(ExtensionConstants.KAPT, dependencyNotation)

fun DependencyHandlerScope.ktlintRuleset(dependencyNotation: Any) =
    add(ExtensionConstants.KTLINT_RULESET, dependencyNotation)
