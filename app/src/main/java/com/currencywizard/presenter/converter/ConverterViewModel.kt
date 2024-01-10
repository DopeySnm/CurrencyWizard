package com.currencywizard.presenter.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencywizard.data.modules.Currency
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.DataState
import com.currencywizard.data.states.UiState
import com.currencywizard.domain.ConvertCurrencyUseCase
import com.currencywizard.domain.GetCurrenciesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

class ConverterViewModel
@Inject constructor(
    val getCurrenciesUseCase: GetCurrenciesUseCase,
    val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _currencies = MutableLiveData<UiState<List<Currency>>>(UiState.Loading)
    private val _baseValue = MutableLiveData(0.0)
    private val _rate = MutableLiveData<UiState<Rate>>(UiState.Loading)

    val currencies : LiveData<UiState<List<Currency>>>
        get() = _currencies
    val rate : LiveData<UiState<Rate>>
        get() = _rate
    val baseValue : LiveData<Double>
        get() = _baseValue

    fun loadCurrencies() {
        _currencies.postValue(UiState.Loading)
        viewModelScope.launch{
            val result = getCurrenciesUseCase()
            _currencies.postValue(UiState.fromDataState(result))
        }
    }

    fun loadRate( currencyForm: String, currencyTo: String) {
        _rate.postValue(UiState.Loading)
        viewModelScope.launch {
            val result = convertCurrencyUseCase(_baseValue.value!!, currencyForm, currencyTo)
            _rate.postValue(UiState.fromDataState(result))
        }
    }


    fun addDigitToBase(digit: Byte){
        _baseValue.postValue((baseValue.value!!.times(10)).plus(digit))
    }
    fun resetBase(){
        _baseValue.postValue(0.0)
    }
    fun resetTarget(){
        _rate.postValue(UiState.Loading)
    }
}