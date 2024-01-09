package com.currencywizard.domain

import com.currencywizard.DateHelper
import com.currencywizard.PeriodsTime
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.repository.CurrencyRepository
import com.currencywizard.data.states.DataState
import javax.inject.Inject

interface GetCurrencyHistoryRelationOverThreeMonthsUseCase {
    suspend operator fun invoke(from: String, to: String): DataState<List<Rate>>
}

class GetCurrencyHistoryRelationOverThreeMonthsUseCaseImpl @Inject constructor(
    private val repository: CurrencyRepository
) : GetCurrencyHistoryRelationOverThreeMonthsUseCase {
    override suspend fun invoke(from: String, to: String): DataState<List<Rate>> {
        return repository.getHistoryRelations(
            startDate=DateHelper.getDateOverInFormatted(PeriodsTime.OVER_THREE_MONTHS),
            endDate=DateHelper.getCurrentDateInFormatted(),
            from=from,
            to=to
        )
    }
}