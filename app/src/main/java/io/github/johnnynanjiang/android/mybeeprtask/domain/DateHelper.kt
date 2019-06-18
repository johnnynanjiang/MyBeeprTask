package io.github.johnnynanjiang.android.mybeeprtask.domain

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {
    companion object {
        private const val DATE_FORMAT = "dd/MM/yyyy"

        fun getDateFromString(date: String): Date = SimpleDateFormat(DATE_FORMAT).parse(date)

        fun getStringFromDate(date: Date): String = SimpleDateFormat(DATE_FORMAT).format(date)

        fun getDate(date: Int, month: Int, year: Int): Date {
            val d = Calendar.getInstance()
            d.set(year, month - 1, date)
            return d.time
        }

        fun isWeekend(date: Date): Boolean {
            val calendar = Calendar.getInstance()
            calendar.time = date

            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                return true
            }

            return false
        }

        fun getFormattedDateString(day: Int, month: Int, year: Int) = String.format("%s/%s/%s", day, month, year)

        fun getYear(date: String): Int {
            val d = getDateFromString(date)
            val cal = Calendar.getInstance()
            cal.time = d
            return cal.get(Calendar.YEAR)
        }
    }
}
