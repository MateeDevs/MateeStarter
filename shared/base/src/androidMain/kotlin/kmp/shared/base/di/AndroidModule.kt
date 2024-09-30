package kmp.shared.base.di

import io.ktor.client.engine.android.Android
import kmp.shared.base.system.Config
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::Config)
    single { Android.create() }
}
