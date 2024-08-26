package kmp.shared.base.di

import io.ktor.client.engine.darwin.Darwin
import kmp.shared.base.error.ErrorMessageProvider
import kmp.shared.base.error.ErrorMessageProviderImpl
import kmp.shared.base.system.Config
import kmp.shared.base.system.ConfigImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::ConfigImpl) bind Config::class
    single { Darwin.create() }
    singleOf(::ErrorMessageProviderImpl) bind ErrorMessageProvider::class
}
