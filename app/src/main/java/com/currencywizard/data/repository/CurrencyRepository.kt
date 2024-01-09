package com.currencywizard.data.repository

import com.currencywizard.data.api.ConvertorService
import com.currencywizard.data.db.RateDao
import javax.inject.Inject

interface CurrencyRepository {

}

class CurrencyRepositoryImpl @Inject constructor(
    private val service: ConvertorService,
    private val dao: RateDao
) : CurrencyRepository{

}