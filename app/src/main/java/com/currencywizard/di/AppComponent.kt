package com.currencywizard.di

import com.currencywizard.presenter.converter.ConverterFragment
import dagger.Component

@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
    fun inject(fragment: ConverterFragment)
}
