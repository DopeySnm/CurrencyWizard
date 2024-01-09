package com.currencywizard.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppBindsModule {


    companion object {
        @Provides
        fun providesContext(app: Application): Context =
            app.applicationContext
    }
}