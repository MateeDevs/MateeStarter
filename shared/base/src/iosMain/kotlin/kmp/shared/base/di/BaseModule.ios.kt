package kmp.shared.base.di

import io.ktor.client.engine.darwin.Darwin
import kmp.shared.base.data.keychain.KeychainFactory
import kmp.shared.base.data.userdefaults.UserDefaultsFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val basePlatformModule = module {
    single { Darwin.create() }
    factoryOf(::KeychainFactory)
    factoryOf(::UserDefaultsFactory)
}
