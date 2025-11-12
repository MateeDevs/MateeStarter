package kmp.shared.samplefeature.di

import kmp.shared.samplefeature.data.repository.JokeRepositoryImpl
import kmp.shared.samplefeature.data.source.JokeSource
import kmp.shared.samplefeature.data.source.impl.JokeSourceImpl
import kmp.shared.samplefeature.domain.repository.JokeRepository
import kmp.shared.samplefeature.domain.usecase.GetRandomJokeUseCase
import kmp.shared.samplefeature.domain.usecase.GetRandomJokeUseCaseImpl
import kmp.shared.samplefeature.data.service.JokeService
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sampleFeatureModule = module {
    // View Models
    viewModelOf(::SampleFeatureViewModel)

    // Use Cases
    factoryOf(::GetRandomJokeUseCaseImpl) bind GetRandomJokeUseCase::class

    // Repositories
    singleOf(::JokeRepositoryImpl) bind JokeRepository::class

    // Sources
    singleOf(::JokeSourceImpl) bind JokeSource::class

    // Services
    singleOf(::JokeService)
}
