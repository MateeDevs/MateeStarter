package kmp.shared.base.di

import io.ktor.client.engine.darwin.Darwin
import kmp.shared.base.system.Config
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::Config)
    single { Darwin.create() }
}
