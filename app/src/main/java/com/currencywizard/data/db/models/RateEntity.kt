package com.currencywizard.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currencywizard.data.db.RateSource
import com.currencywizard.data.modules.Rate

@Entity(tableName = "rates")
data class RateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val base: String,
    val target: String,
    val date: String,
    val coefficient: Double,
    val source: RateSource = RateSource.TRANSFER
) {
    fun toRate(): Rate =
        Rate(
            id, base, target, date, coefficient
        )
}
