package com.currencywizard.di.modules

import android.app.Application
import android.content.Context
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.repository.CurrencyRepositoryImpl
import com.currencywizard.domain.GetCurrenciesUseCase
import com.currencywizard.domain.GetCurrenciesUseCaseImpl
import com.currencywizard.domain.GetCurrencyHistoryRelationOverFiveYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverFiveYearsUseCaseImpl
import com.currencywizard.domain.GetCurrencyHistoryRelationOverOneYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverOneYearsUseCaseImpl
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeMonthsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeMonthsUseCaseImpl
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeYearsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppBindsModule {

    @Binds
    fun bindGetCurrencyHistoryRelationOverThreeYearsUseCase(
        useCase: GetCurrencyHistoryRelationOverThreeYearsUseCaseImpl
    ): GetCurrencyHistoryRelationOverThreeYearsUseCase
    @Binds
    fun bindGetCurrencyHistoryRelationOverThreeMonthsUseCase(
        useCase: GetCurrencyHistoryRelationOverThreeMonthsUseCaseImpl
    ): GetCurrencyHistoryRelationOverThreeMonthsUseCase
    @Binds
    fun bindGetCurrencyHistoryRelationOverOneYearsUseCase(
        useCase: GetCurrencyHistoryRelationOverOneYearsUseCaseImpl
    ): GetCurrencyHistoryRelationOverOneYearsUseCase
    @Binds
    fun bindGetCurrencyHistoryRelationOverFiveYearsUseCase(
        useCase: GetCurrencyHistoryRelationOverFiveYearsUseCaseImpl
    ): GetCurrencyHistoryRelationOverFiveYearsUseCase

    @Binds
    fun bindGetCurrenciesUseCase(useCase: GetCurrenciesUseCaseImpl): GetCurrenciesUseCase

    @Binds
    fun bindCurrencyRepository(repository: CurrencyRepositoryImpl) : CurrencyRepository

    companion object {
        @Provides
        fun providesContext(app: Application): Context =
            app.applicationContext
    }
}