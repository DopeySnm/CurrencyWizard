package com.currencywizard.data.api

import com.currencywizard.data.api.models.CurrencyRelationsResponse
import com.currencywizard.data.api.models.HistoryCurrencyRelationsResponse
import com.currencywizard.data.repository.models.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConvertorService {

    @GET("latest")
    fun getCurrencyRelations(
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<CurrencyRelationsResponse>

    @GET("currencies")
    fun getCurrencies(): Response<Map<String, String>> //symbol - name

    @GET("{startDate}..{endDate}")
    fun getHistoryCurrencyRelations(
        @Path("startDate") startDate: String, //example format date 2020-01-01
        @Path("endDate") endDate: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<HistoryCurrencyRelationsResponse>

}