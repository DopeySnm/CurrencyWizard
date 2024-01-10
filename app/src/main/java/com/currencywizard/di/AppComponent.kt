package com.currencywizard.di

import android.app.Application
import com.currencywizard.presenter.converter.ConverterFragment
import com.currencywizard.presenter.historyRelation.HistoryRelationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
    fun inject(fragment: ConverterFragment)

    fun inject(fragment: HistoryRelationFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}
