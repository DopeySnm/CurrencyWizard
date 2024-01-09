package com.currencywizard.di.modules

import android.content.Context
import androidx.room.Room
import com.currencywizard.data.db.CurrencyDao
import com.currencywizard.data.db.CurrencyDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): CurrencyDatabase =
        Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            "currency.db"
        ).build()


    @Provides
    fun provideCurrencyDao(db: CurrencyDatabase): CurrencyDao {
        return db.currencyDao()
    }
}