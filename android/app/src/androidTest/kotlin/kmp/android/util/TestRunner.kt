package kmp.android.util

import android.app.Application
import androidx.test.runner.AndroidJUnitRunner
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Test runner used for all the tests
 */
class TestRunner : AndroidJUnitRunner(), KoinTest {

    /**
     * Runs when application is created
     */
    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)

        val testModule = module {
            // place test implementations of injected classes here
        }

        loadKoinModules(
            testModule,
        )
    }
}
