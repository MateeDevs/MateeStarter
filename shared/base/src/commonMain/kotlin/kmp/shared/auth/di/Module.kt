package kmp.shared.auth.di

import kmp.shared.auth.infrastructure.remote.AuthService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    // Services
    singleOf(::AuthService)
}
