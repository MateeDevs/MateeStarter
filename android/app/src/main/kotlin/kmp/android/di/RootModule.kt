package kmp.android.di

import android.app.Application
import android.content.Context
import kmp.shared.umbrella.di.initKoin
import org.koin.dsl.module

fun Application.initDependencyInjection() {
    initKoin {
        val contextModule = module { // Provide Android Context
            factory<Context> { this@initDependencyInjection }
        }

        modules(
            contextModule,
        )
    }
}
