package io.github.johnnynanjiang.android.mybeeprtask.domain

import java.util.*

import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getDate
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getStringFromDate
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.isWeekend

interface Holiday {
    fun getDateInYear(year: Int): String
}

class FixedHoliday(val date: Int, val month: Int, val name: String = "") : Holiday {
    override fun getDateInYear(year: Int): String = getStringFromDate(getDate(date, month, year))
}

class FlexibleHoliday(val date: Int, val month: Int, val name: String = "") : Holiday {
    private fun getActualDateInYear(year: Int): Date {
        val date = getDate(date, month, year)

        if (isWeekend(date)) {
            var cal = Calendar.getInstance()
            cal.time = date

            do {
                cal.add(Calendar.DAY_OF_MONTH, 1)
            } while (isWeekend(cal.time))

            return cal.time
        }

        return date
    }

    override fun getDateInYear(year: Int): String = getStringFromDate(getActualDateInYear(year))
}


class GoodFridayHoliday : Holiday {
    fun getEasterSundayDate(year: Int): Date {
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val g = (8 * b + 13) / 25
        val h = (19 * a + b - d - g + 15) % 30
        val j = c / 4
        val k = c % 4
        val m = (a + 11 * h) / 319
        val r = (2 * e + 2 * j - k - h + m + 32) % 7
        val n = (h - m + r + 90) / 25
        val p = (h - m + r + n + 19) % 32

        return getDate(p, n, year)
    }

    override fun getDateInYear(year: Int): String {
        val cal = Calendar.getInstance()
        cal.time = getEasterSundayDate(year)
        cal.add(Calendar.DAY_OF_MONTH, -2)
        return getStringFromDate(cal.time)
    }
}

class EasterMondayHoliday : Holiday {
    override fun getDateInYear(year: Int): String {
        val cal = Calendar.getInstance()
        cal.time = GoodFridayHoliday().getEasterSundayDate(year)
        cal.add(Calendar.DAY_OF_MONTH, 1)
        return getStringFromDate(cal.time)
    }
}

class QueensBirthdayHoliday : Holiday {
    private fun getSecondMondayOfJune(year: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, Calendar.JUNE)
        cal.firstDayOfWeek = Calendar.SATURDAY // TODO - Calendar.SUNDAY doesn't work, need to investigate
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.WEEK_OF_MONTH, 2)
        return getStringFromDate(cal.time)
    }

    override fun getDateInYear(year: Int): String = getSecondMondayOfJune(year)
}