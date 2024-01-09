package com.currencywizard.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.currencywizard.di.viewModel.ViewModelFactory
import com.currencywizard.di.viewModel.ViewModelKey
import com.currencywizard.presenter.converter.ConverterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    abstract fun bindConverterViewModel(viewModel: ConverterViewModel): ViewModel
}