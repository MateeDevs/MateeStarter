package kmp.shared.base.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformModule = module {
    single { Darwin.create() }
}
