package io.github.johnnynanjiang.android.mybeeprtask.domain

import java.util.*

class BusinessDayCalculator {
    fun getNumberOfBusinessDaysBetween(from: String, to: String, holidays: List<String> = emptyList()) =
        getNumberOfBusinessDaysBetween(
            DateHelper.getDateFromString(from),
            DateHelper.getDateFromString(to),
            holidays.map { DateHelper.getDateFromString(it) })

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
        if (DateHelper.isWeekend(calendar.time)) {
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