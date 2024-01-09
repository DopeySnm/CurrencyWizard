package com.currencywizard.di.modules

import com.currencywizard.data.api.ConvertorService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideConvertorService(): ConvertorService {
        return Retrofit.Builder()
            .baseUrl("https://www.frankfurter.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConvertorService::class.java)
    }
}