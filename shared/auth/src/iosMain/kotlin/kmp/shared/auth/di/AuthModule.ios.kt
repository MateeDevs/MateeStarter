package kmp.shared.auth.di

import kmp.shared.auth.data.provider.AuthProviderImpl
import kmp.shared.base.data.keychain.KeychainFactory
import kmp.shared.base.data.provider.AuthProvider
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val authPlatformModule: Module = module {
    single<AuthProvider> {
        AuthProviderImpl(
            settings = get<KeychainFactory>().create(),
            tokenRefresher = get(),
        )
    }
}
