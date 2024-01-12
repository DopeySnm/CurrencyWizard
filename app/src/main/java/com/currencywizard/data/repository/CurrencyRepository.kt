package com.currencywizard.data.repository

import com.currencywizard.DateHelper
import com.currencywizard.data.api.ConvertorService
import com.currencywizard.data.db.RateDao
import com.currencywizard.data.db.RateSource
import com.currencywizard.data.db.models.RateEntity
import com.currencywizard.data.modules.Currency
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.DataState
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

interface CurrencyRepository {

    suspend fun convertCurrency(
        amount: Double,
        currencyForm: String,
        currencyTo: String
    ): DataState<Rate>

    suspend fun getCurrencies(): DataState<List<Currency>>

    suspend fun getHistoryRelations(
        startDate: String,
        endDate: String,
        from: String,
        to: String
    ): DataState<List<Rate>>

    suspend fun getTransferHistory(): DataState<List<Rate>>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val service: ConvertorService,
    private val dao: RateDao
) : CurrencyRepository {
    override suspend fun convertCurrency(
        amount: Double,
        currencyForm: String,
        currencyTo: String
    ): DataState<Rate> {


        kotlin.runCatching {
            service.getCurrencyRelations(currencyForm, currencyTo, amount)
        }.fold(
            onSuccess = {
                return if (it.isSuccessful) {
                    it.body()?.let { currencyRelations ->
                        val rate = Rate(
                            base = currencyForm,
                            target = currencyTo,
                            date = currencyRelations.date,
                            result = currencyRelations.rates[currencyTo]!!,
                            amount = amount
                        )
                        dao.saveRate(rate.toRateEntity(RateSource.TRANSFER))
                        DataState.Success(rate)
                    } ?: DataState.Failure("Empty response")
                } else DataState.Failure("Unable to convert currency")
            },
            onFailure = {
                return DataState.Failure(it.message ?: "Unknown error")
            }
        )
    }

    override suspend fun getCurrencies(): DataState<List<Currency>> {
        kotlin.runCatching {
            service.getCurrencies()
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    it.body()?.let { currencies ->
                        DataState.Success(currencies.entries.map { (symbol, name) ->
                            Currency(symbol, name)
                        })
                    } ?: DataState.Failure("Empty response")
                else DataState.Failure("Unable to get currencies")
            },
            onFailure = {
                return DataState.Failure(it.message ?: "Unknown error")
            }
        )
    }

    override suspend fun getHistoryRelations(
        startDate: String,
        endDate: String,
        from: String,
        to: String
    ): DataState<List<Rate>> {
        val savedInfo: List<Rate> =
            orderByDate(dao.getRelationHistory(from, to).map { it.toRate() })

        if (cacheValidate(savedInfo, startDate, endDate))
            return DataState.Success(filterByDate(savedInfo, startDate, endDate))

        else {
            kotlin.runCatching {
                service.getHistoryCurrencyRelations(
                    startDate,
                    endDate,
                    from,
                    to
                )
            }.fold(
                onSuccess = {
                    return if (it.isSuccessful)
                        it.body()?.let { historyCurrencyRelations ->
                            val listRate = historyCurrencyRelations.rates.entries.map { rates ->
                                val ratePair: List<Pair<String, Double>> =
                                    rates.value.entries.map { rate ->
                                        Pair(rate.key, rate.value)
                                    }
                                val rate = Rate(
                                    base = historyCurrencyRelations.base,
                                    target = ratePair[0].first,
                                    date = rates.key,
                                    result = ratePair[0].second,
                                    amount = historyCurrencyRelations.amount
                                )
                                rate
                            }
                            dao.refreshCache(from, to, listRate.map {rate -> rate.toRateEntity(RateSource.HISTORY) })
                            DataState.Success(listRate)
                        } ?: DataState.Failure("")
                    else DataState.Failure("Unable to get history relations")
                },
                onFailure = {
                    return DataState.Failure(it.message ?: "Unknown error")
                }
            )
        }
    }

    override suspend fun getTransferHistory(): DataState<List<Rate>> {
        kotlin.runCatching {
            dao.getTransferHistory()
        }.fold(
            onSuccess = {
                return if(it.isNotEmpty()){
                    val result = it.map { rateEntity ->
                        rateEntity.toRate()
                    }
                    DataState.Success(result)
                }
                else return DataState.Failure("Rates is empty")
            },
            onFailure = {
                return DataState.Failure(it.message ?: "Unknown error")
            }
        )
    }

    private fun orderByDate(rates: List<Rate>): List<Rate> {
        return rates.sortedBy { DateHelper.formatter.parse(it.date) }
    }

    private fun filterByDate(rates: List<Rate>, startDate: String, endDate: String): List<Rate> {
        return rates.filter {
            DateHelper.formatter.parse(it.date)!! >= DateHelper.formatter.parse(startDate)
                    && DateHelper.formatter.parse(it.date)!! <= DateHelper.formatter.parse(endDate)
        }
    }

    private fun cacheValidate(rates: List<Rate>, startDate: String, endDate: String): Boolean {
        if (rates.isEmpty()) return false
        val nextUpdate = getDateWeeksLater(DateHelper.formatter.parse(rates.last().date)!!)
        return DateHelper.formatter.parse(rates.first().date)!! <= DateHelper.formatter.parse(
            startDate
        )
                && nextUpdate >= DateHelper.formatter.parse(endDate)
    }

    private fun getDateWeeksLater(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
        return calendar.time
    }
}