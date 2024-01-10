package com.currencywizard.di

import android.app.Application
import com.currencywizard.presenter.converter.ConverterFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
    fun inject(fragment: ConverterFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}
