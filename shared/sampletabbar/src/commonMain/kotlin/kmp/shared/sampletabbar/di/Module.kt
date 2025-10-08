package kmp.shared.sampletabbar.di

import kmp.shared.sampletabbar.presentation.vm.SampleTabBarViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sampleTabBarModule = module {
    viewModelOf(::SampleTabBarViewModel)
}