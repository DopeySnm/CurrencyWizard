package com.currencywizard.presenter.transferHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.UiState
import kotlinx.coroutines.launch

class TransferHistoryViewModel : ViewModel() {

    private val _rates = MutableLiveData<UiState<List<Rate>>>(UiState.Loading)
    val rates : LiveData<UiState<List<Rate>>>
        get() = _rates

    fun loadRates() {
        _rates.postValue(UiState.Loading)
        viewModelScope.launch {
           /* val result = convertCurrencyUseCase(_baseValue.value!!, currencyForm, currencyTo)
            _rates.postValue(UiState.fromDataState(result))*/
        }
    }
}