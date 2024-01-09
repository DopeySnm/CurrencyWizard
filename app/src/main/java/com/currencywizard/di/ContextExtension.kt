package com.currencywizard.di

import android.content.Context
import com.currencywizard.presenter.MainApp

val Context.appComponent: AppComponent
    get() = when(this){
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }