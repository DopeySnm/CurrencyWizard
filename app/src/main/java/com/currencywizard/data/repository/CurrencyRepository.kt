package com.currencywizard.data.repository

import com.currencywizard.data.api.ConvertorService
import com.currencywizard.data.db.CurrencyDao
import com.currencywizard.data.repository.models.Currency
import javax.inject.Inject

interface CurrencyRepository {

    suspend fun convertCurrency(
        amount: Double,
        currencyForm: String,
        currencyTo: String
    ): Result<Double?>

    suspend fun getCurrencies(): Result<List<Currency>?>

    suspend fun getHistoryRelations(
        startDate: String,
        endDate: String,
        from: String,
        to: String
    ) : Result<List<Rate>>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val service: ConvertorService,
    private val dao: CurrencyDao
) : CurrencyRepository{

    override suspend fun convertCurrency(amount: Double, currencyForm: String, currencyTo: String): Result<Double?> {
        kotlin.runCatching {
            service.getCurrencyRelations(currencyForm, currencyTo)
        }.fold(
            onSuccess = {
                return if(it.isSuccessful)
                    Result.success(it.body()?.rates?.get(currencyTo)?.times(amount))
                else Result.failure(retrofit2.HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun getCurrencies(): Result<List<Currency>?> {
        kotlin.runCatching {
            service.getCurrencies()
        }.fold(
            onSuccess = {
                return if(it.isSuccessful)
                    Result.success(it.body()?.entries?.map { (symbol, name) ->
                        Currency(symbol, name)
                    })
                else Result.failure(retrofit2.HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun getHistoryRelations(
        startDate: String,
        endDate: String,
        from: String,
        to: String
    ): Result<List<Rate>> {
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
                    
                else Result.failure(retrofit2.HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }


}