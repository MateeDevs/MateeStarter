package kmp.shared.base.di

import com.russhwolf.settings.Settings
import kmp.shared.base.data.remote.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val baseModule = module {

    includes(basePlatformModule)

    // General
    singleOf(HttpClient::init)
    singleOf(::Settings)
}

internal expect val basePlatformModule: Module
