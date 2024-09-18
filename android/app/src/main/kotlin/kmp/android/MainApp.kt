package kmp.android

import android.app.Application
import kmp.android.di.initDependencyInjection

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initDependencyInjection()
    }
}