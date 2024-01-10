package com.currencywizard.presenter.historyRelation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.UiState
import com.currencywizard.domain.GetCurrencyHistoryRelationOverFiveYearsUseCase
import com.currencywizard.domain.GetCurrencyHistoryRelationOverThreeMonthsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryRelationViewModel @Inject constructor(
    private val getHistoryOverFiveYearsUseCase: GetCurrencyHistoryRelationOverFiveYearsUseCase
) : ViewModel() {

    private val _ratesLiveData = MutableLiveData<UiState<List<Rate>>>(UiState.Loading)
    val ratesLiveData: LiveData<UiState<List<Rate>>>
        get() = _ratesLiveData


    fun loadHistoryOveFiveYears(from: String, to: String) {
        viewModelScope.launch {
            val date = getHistoryOverFiveYearsUseCase(to, from)
            _ratesLiveData.postValue(UiState.fromDataState(date))
        }
    }


}