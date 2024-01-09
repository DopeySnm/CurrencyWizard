package com.currencywizard.domain

import com.currencywizard.data.modules.Rate
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.states.DataState
import javax.inject.Inject

interface ConvertCurrencyUseCase {
    suspend operator fun invoke(
        amount: Double,
        currencyForm: String,
        currencyTo: String
    ): DataState<Rate>
}

class ConvertCurrencyUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) : ConvertCurrencyUseCase {
    override suspend fun invoke(
        amount: Double,
        currencyForm: String,
        currencyTo: String
    ): DataState<Rate> {
        return repository.convertCurrency(amount, currencyForm, currencyTo)
    }

}