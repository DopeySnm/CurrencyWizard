package com.currencywizard.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.currencywizard.di.viewModel.ViewModelFactory
import com.currencywizard.di.viewModel.ViewModelKey
import com.currencywizard.presenter.converter.ConverterViewModel
import com.currencywizard.presenter.historyRelation.HistoryRelationViewModel
import com.currencywizard.presenter.transferHistory.TransferHistoryViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(HistoryRelationViewModel::class)
    abstract fun bindHistoryRelationViewModel(viewModel: HistoryRelationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransferHistoryViewModel::class)
    abstract fun bindTransferHistoryViewModel(viewModel: TransferHistoryViewModel): ViewModel
}