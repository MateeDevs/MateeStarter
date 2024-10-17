package kmp.shared.analytics.di

import kmp.shared.analytics.data.repository.AnalyticsRepositoryImpl
import kmp.shared.analytics.domain.repository.AnalyticsRepository
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    // Use cases
    factoryOf(::TrackAnalyticsEventUseCaseImpl) bind TrackAnalyticsEventUseCase::class

    // Repositories
    singleOf(::AnalyticsRepositoryImpl) bind AnalyticsRepository::class
}