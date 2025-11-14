package kmp.shared.analytics.di

import kmp.shared.analytics.data.provider.AnalyticsProvider
import kmp.shared.analytics.data.provider.AndroidAnalyticsProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val analyticsPlatformModule: Module = module {
    singleOf(::AndroidAnalyticsProviderImpl) bind AnalyticsProvider::class
}
