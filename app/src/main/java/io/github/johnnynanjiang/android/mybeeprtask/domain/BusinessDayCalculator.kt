package io.github.johnnynanjiang.android.mybeeprtask.domain

import java.text.SimpleDateFormat
import java.util.*

class BusinessDayCalculator {
    private companion object {
        const val DATE_FORMAT = "dd/MM/yyyy"
    }

    fun getDateFromString(date: String): Date =
        SimpleDateFormat(DATE_FORMAT).parse(date)

    fun getNumberOfBusinessDaysBetween(from: String, to: String, holidays: List<String> = emptyList()) =
        getNumberOfBusinessDaysBetween(getDateFromString(from), getDateFromString(to), holidays.map { getDateFromString(it) })

    private fun getNumberOfBusinessDaysBetween(from: Date, to: Date, holidays: List<Date> = emptyList()): Int {
        val startCal = Calendar.getInstance()
        startCal.time = from

        val endCal = Calendar.getInstance()
        endCal.time = to

        var numberOfBusinessDays = 0

        if (startCal.timeInMillis === endCal.timeInMillis) {
            return 0
        }

        if (startCal.timeInMillis > endCal.timeInMillis) {
            startCal.time = to
            endCal.time = from
        }

        //excluding start date
        startCal.add(Calendar.DAY_OF_MONTH, 1)

        //excluding end date
        endCal.add(Calendar.DAY_OF_MONTH, -1)

        while (startCal.timeInMillis <= endCal.timeInMillis) {
            if (!isHoliday(startCal, holidays)) {
                ++numberOfBusinessDays
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1)
        }

        return numberOfBusinessDays
    }

    private fun isHoliday(calendar: Calendar, holidays: List<Date>): Boolean {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        if ( dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return true
        }

        if (holidays.isEmpty()) {
            return false
        }

        for (holiday in holidays) {
            val holidayCal = Calendar.getInstance()
            holidayCal.time = holiday

            if (calendar.timeInMillis == holidayCal.timeInMillis) {
                return true
            }
        }

        return false
    }
}