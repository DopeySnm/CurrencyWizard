package com.currencywizard.presenter.historyRelation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.UiState
import com.currencywizard.domain.GetCurrencyHistoryRelationOverFiveYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverOneYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeMonthsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeYearsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryRelationViewModel @Inject constructor(
    private val getHistoryForTheLastThreeMonthsUseCase: GetCurrencyHistoryRelationOverThreeMonthsUseCase,
    private val getHistoryForTheLastOneYearsUseCase: GetCurrencyHistoryRelationOverOneYearsUseCase,
    private val getHistoryForTheLastThreeYearsUseCase: GetCurrencyHistoryRelationOverThreeYearsUseCase,
    private val getHistoryForTheLastFiveYearsUseCase: GetCurrencyHistoryRelationOverFiveYearsUseCase
) : ViewModel() {

    private val _ratesLiveData = MutableLiveData<UiState<List<Rate>>>(UiState.Loading)
    val ratesLiveData: LiveData<UiState<List<Rate>>>
        get() = _ratesLiveData

    fun loadForTheLastThreeMonths(from: String, to: String) {
        viewModelScope.launch {
            val date = getHistoryForTheLastThreeMonthsUseCase(to, from)
            _ratesLiveData.postValue(UiState.fromDataState(date))
        }
    }

    fun loadForTheLastOneYears(from: String, to: String) {
        viewModelScope.launch {
            val date = getHistoryForTheLastOneYearsUseCase(to, from)
            _ratesLiveData.postValue(UiState.fromDataState(date))
        }
    }

    fun loadForTheLastThreeYears(from: String, to: String) {
        viewModelScope.launch {
            val date = getHistoryForTheLastThreeYearsUseCase(to, from)
            _ratesLiveData.postValue(UiState.fromDataState(date))
        }
    }

    fun loadForTheLastFiveYears(from: String, to: String) {
        viewModelScope.launch {
            val date = getHistoryForTheLastFiveYearsUseCase(to, from)
            _ratesLiveData.postValue(UiState.fromDataState(date))
        }
    }

}