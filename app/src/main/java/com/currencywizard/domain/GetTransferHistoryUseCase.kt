package com.currencywizard.domain

import com.currencywizard.data.modules.Rate
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.states.DataState
import javax.inject.Inject

interface GetTransferHistoryUseCase {
    suspend operator fun invoke(): DataState<List<Rate>>
}

class GetTransferHistoryUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) : GetTransferHistoryUseCase {
    override suspend fun invoke(): DataState<List<Rate>> {
        return repository.getTransferHistory()
    }
}