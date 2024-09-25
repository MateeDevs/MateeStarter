package kmp.shared.base.di

import com.russhwolf.settings.Settings
import kmp.shared.base.infrastucture.provider.AuthProvider
import kmp.shared.base.infrastucture.provider.AuthProviderImpl
import kmp.shared.base.infrastucture.remote.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val baseModule = module {

    includes(platformModule)

    // General
    singleOf(HttpClient::init)
    singleOf(::Settings)

    // Providers
    singleOf(::AuthProviderImpl) bind AuthProvider::class
}

internal expect val platformModule: Module
