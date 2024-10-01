package kmp.android.di

import android.app.Application
import android.content.Context
import kmp.android.sample.di.androidSampleModule
import kmp.android.shared.di.androidSharedModule
import kmp.shared.core.di.initKoin
import org.koin.dsl.module

fun Application.initDependencyInjection() {
    initKoin {
        val contextModule = module { // Provide Android Context
            factory<Context> { this@initDependencyInjection }
        }

        modules(
            contextModule,
            androidSharedModule,
            androidSampleModule,
        )
    }
}
