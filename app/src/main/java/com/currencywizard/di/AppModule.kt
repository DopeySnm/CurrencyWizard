package com.currencywizard.di

import com.currencywizard.di.modules.AppBindsModule
import com.currencywizard.di.modules.NetworkModule
import com.currencywizard.di.modules.ViewModelModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        ViewModelModule::class,
        AppBindsModule::class
    ]
)
class AppModule