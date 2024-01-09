package com.currencywizard.presenter.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.currencywizard.data.modules.Currency
import javax.inject.Inject

class ConverterViewModel
@Inject constructor(

) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies : LiveData<List<Currency>>
        get() = _currencies

    fun loadCurrencies(){
        _currencies.postValue(listOf(Currency("AJN", "hello "), Currency("ERT", "world")))
    }
}