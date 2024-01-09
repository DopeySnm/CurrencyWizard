package com.currencywizard.domain

import com.currencywizard.DateHelper
import com.currencywizard.PeriodsTime
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.states.DataState
import javax.inject.Inject

interface GetCurrencyHistoryRelationOverFiveYearsUseCase {
    suspend operator fun invoke(from: String, to: String): DataState<List<Rate>>
}

class GetCurrencyHistoryRelationOverFiveYearsUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) : GetCurrencyHistoryRelationOverFiveYearsUseCase {
    override suspend fun invoke(from: String, to: String): DataState<List<Rate>> {
        return repository.getHistoryRelations(
            startDate=DateHelper.getDateOverInFormatted(PeriodsTime.OVER_FIVE_YEARS),
            endDate=DateHelper.getCurrentDateInFormatted(),
            from=from,
            to=to
        )
    }
}