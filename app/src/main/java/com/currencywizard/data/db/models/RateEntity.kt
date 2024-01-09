package com.currencywizard.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currencywizard.data.db.RateSource

@Entity(tableName = "rates")
data class RateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val base: String,
    val target: String,
    val coefficient: Double,
    val source: RateSource = RateSource.TRANSFER
)
