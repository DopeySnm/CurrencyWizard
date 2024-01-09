package com.currencywizard.data.api.models

data class CurrencyRelationsResponse(
    val amount: Int,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)