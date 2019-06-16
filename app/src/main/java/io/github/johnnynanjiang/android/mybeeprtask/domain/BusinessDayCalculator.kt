package io.github.johnnynanjiang.android.mybeeprtask.domain

import java.text.SimpleDateFormat
import java.util.*

class BusinessDayCalculator {

    fun getDateFromString(date: String) =
        SimpleDateFormat("dd/MM/yyyy").parse(date)

    fun getWeekDayOf(date: String) =
        getWeekDayOf(getDateFromString(date))

    fun getNumberOfWeekDaysBetween(from: String, to: String) =
        getNumberOfWeekDaysBetween(getDateFromString(from), getDateFromString(to))

    fun getWeekDayOf(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun getNumberOfWeekDaysBetween(from: Date, to: Date): Int {
        val startCal = Calendar.getInstance()
        startCal.time = from

        val endCal = Calendar.getInstance()
        endCal.time = to

        var workDays = 0

        //Return 0 if start and end are the same
        if (startCal.timeInMillis === endCal.timeInMillis) {
            return 0
        }

        if (startCal.timeInMillis > endCal.timeInMillis) {
            startCal.time = to
            endCal.time = from
        }

        //excluding end date
        endCal.add(Calendar.DAY_OF_MONTH, -1)

        while (startCal.timeInMillis < endCal.timeInMillis) {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1)

            if (startCal.get(Calendar.DAY_OF_WEEK) !== Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) !== Calendar.SUNDAY) {
                ++workDays
            }
        }

        return workDays
    }
}