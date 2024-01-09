package com.currencywizard.di.modules

import android.app.Application
import android.content.Context
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppBindsModule {
    
    @Binds
    fun bindCurrencyRepository(repository: CurrencyRepositoryImpl) : CurrencyRepository

    companion object {
        @Provides
        fun providesContext(app: Application): Context =
            app.applicationContext
    }
}