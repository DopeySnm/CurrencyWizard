package com.currencywizard.data.modules

import com.currencywizard.data.db.RateSource
import com.currencywizard.data.db.models.RateEntity
import java.util.Date


data class Rate(
    val id: Int = 0,
    val base: String,
    val target: String,
    val date: String,
    val coefficient: Double
) {
    fun toRateEntity(source: RateSource): RateEntity =
        RateEntity(
            id, base, target, date, coefficient, source
        )
}