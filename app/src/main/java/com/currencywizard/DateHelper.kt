package com.currencywizard

import java.text.SimpleDateFormat
import java.util.Calendar

enum class PeriodsTime(val value: Int, val period: Int) {
    OVER_FIVE_YEARS(-5, Calendar.YEAR),
    OVER_THREE_YEARS(-3, Calendar.YEAR),
    OVER_ONE_YEARS(-1, Calendar.YEAR),
    OVER_THREE_MONTHS(-3, Calendar.MONTH)
}

class DateHelper {

    companion object {
        private val formatter = SimpleDateFormat("YYYY-MM-DD")

        fun getCurrentDateInFormatted(): String {
            val time = Calendar.getInstance().time
            return formatter.format(time)
        }

        fun getDateOverInFormatted(periodsTime: PeriodsTime): String {
            val calendar = Calendar.getInstance()
            calendar.add(periodsTime.period, periodsTime.value)
            val time = calendar.time
            return formatter.format(time)
        }
    }

}