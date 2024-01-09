package com.currencywizard.domain

import com.currencywizard.data.modules.Currency
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.states.DataState
import javax.inject.Inject

interface GetCurrenciesUseCase {
    suspend operator fun invoke(): DataState<List<Currency>>
}
class GetCurrenciesUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) :GetCurrenciesUseCase {
    override suspend fun invoke(): DataState<List<Currency>> {
       return repository.getCurrencies()
    }
}