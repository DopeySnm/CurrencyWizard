package com.currencywizard

import android.app.Application
import com.currencywizard.di.AppComponent
import com.currencywizard.di.DaggerAppComponent

class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}