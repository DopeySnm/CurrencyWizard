package com.currencywizard.di.modules

import android.content.Context
import androidx.room.Room
import com.currencywizard.data.db.RateDao
import com.currencywizard.data.db.RateDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): RateDatabase =
        Room.databaseBuilder(
            context,
            RateDatabase::class.java,
            "currency.db"
        ).build()


    @Provides
    fun provideCurrencyDao(db: RateDatabase): RateDao {
        return db.currencyDao()
    }
}