package kmp.shared.base.di

import io.ktor.client.engine.android.Android
import kmp.shared.base.system.Config
import kmp.shared.base.system.ConfigImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::ConfigImpl) bind Config::class
    single { Android.create() }
}
