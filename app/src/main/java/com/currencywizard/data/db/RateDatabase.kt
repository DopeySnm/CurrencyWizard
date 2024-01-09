package com.currencywizard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [

    ],
    version = 1,
    exportSchema = true
)
abstract class RateDatabase : RoomDatabase() {
    abstract fun currencyDao(): RateDao
}