package com.currencywizard.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters



enum class RateSource {
    TRANSFER,
    HISTORY;

    class Converter{
        @TypeConverter
        fun toRateSource(value: String) = enumValueOf<RateSource>(value)

        @TypeConverter
        fun fromRateSource(value: RateSource) = value.name

    }
}