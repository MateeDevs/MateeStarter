package kmp.shared.core.di

import kmp.shared.analytics.di.analyticsModule
import kmp.shared.auth.di.authModule
import kmp.shared.base.di.baseModule
import kmp.shared.sample.di.sampleModule
import kmp.shared.samplecomposemultiplatform.di.sampleComposeMultiplatformModule
import kmp.shared.samplecomposenavigation.di.sampleComposeNavigationModule
import kmp.shared.samplesharedviewmodel.di.sampleSharedViewModelModule
import kmp.shared.sampletabbar.di.sampleTabBarModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        modules(
            baseModule,
            authModule,
            sampleModule,
            sampleSharedViewModelModule,
            analyticsModule,
            sampleComposeMultiplatformModule,
            sampleComposeNavigationModule,
            sampleTabBarModule,
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    val doOnStartup =
        koin.getOrNull<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
    doOnStartup?.invoke()

    return koinApplication
}
