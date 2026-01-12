package kmp.shared.base.di

import io.ktor.client.engine.android.Android
import kmp.shared.base.data.preferences.SharedPreferencesFactory
import kmp.shared.base.domain.system.Config
import kmp.shared.base.domain.system.ConfigImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val basePlatformModule = module {
    singleOf(::ConfigImpl) bind Config::class
    single { Android.create() }

    factoryOf(::SharedPreferencesFactory)
}
