package kmp.shared.auth.di

import kmp.shared.auth.data.remote.AuthService
import kmp.shared.auth.data.remote.MockTokenRefresher
import kmp.shared.auth.data.remote.TokenRefresher
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    includes(authPlatformModule)

    // Services
    singleOf(::AuthService)

    // Mock
    singleOf(::MockTokenRefresher) bind TokenRefresher::class
}

internal expect val authPlatformModule: Module
