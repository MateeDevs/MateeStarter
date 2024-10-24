package kmp.shared.base.di

import io.ktor.client.engine.android.Android
import kmp.shared.analytics.data.provider.AnalyticsProvider
import kmp.shared.base.analytics.AndroidAnalyticsProviderImpl
import kmp.shared.base.system.Config
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::Config)
    single { Android.create() }
    singleOf(::AndroidAnalyticsProviderImpl) bind AnalyticsProvider::class
}
