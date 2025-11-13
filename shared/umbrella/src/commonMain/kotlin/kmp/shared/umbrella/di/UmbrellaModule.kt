package kmp.shared.umbrella.di

import kmp.shared.analytics.di.analyticsModule
import kmp.shared.auth.di.authModule
import kmp.shared.base.di.baseModule
import kmp.shared.samplefeature.di.sampleFeatureModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        modules(
            baseModule,
            authModule,
            analyticsModule,
            sampleFeatureModule,
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    val doOnStartup =
        koin.getOrNull<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
    doOnStartup?.invoke()

    return koinApplication
}
