package com.currencywizard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currencywizard.data.db.models.RateEntity

@Database(
    entities = [
        RateEntity::class
    ],
    version = 1
)
abstract class RateDatabase : RoomDatabase() {
    abstract fun currencyDao(): RateDao
}