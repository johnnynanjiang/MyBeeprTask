package io.github.johnnynanjiang.android.mybeeprtask.service

import io.github.johnnynanjiang.android.mybeeprtask.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HolidayService {
    private companion object {
        val FIXED_HOLIDAYS = listOf(FixedHoliday(25, 4, "Anzac Day"))
        val FLEXIBLE_HOLIDAYS = listOf(
            FlexibleHoliday(1, 1, "New Year's Day"),
            FlexibleHoliday(26, 1, "Australia Day"),
            FlexibleHoliday(5, 8, "Bank Holiday"),
            FlexibleHoliday(7, 10, "Labour Day"),
            FlexibleHoliday(25, 12, "Christmas Day"),
            FlexibleHoliday(26, 12, "Boxing Day")
        )
        val EASTER_HOLIDAYS = listOf(GoodFridayHoliday(), EasterMondayHoliday())
        val OTHER_HOLIDAYS = listOf(QueensBirthdayHoliday())

        val NSW_HOLIDAYS = FIXED_HOLIDAYS
            .union(FLEXIBLE_HOLIDAYS)
            .union(EASTER_HOLIDAYS)
            .union(OTHER_HOLIDAYS)
    }

    fun getHolidaysInYears(fromYear: Int, toYear: Int): List<String> {
        val range: IntRange = fromYear..toYear
        val holidays = arrayListOf<String>()

        for (year in range) {
            holidays.addAll(getHolidaysInYear(year))
        }

        return holidays
    }

    fun getHolidaysInYear(year: Int): List<String> = NSW_HOLIDAYS.map { it.getDateInYear(year) }

    fun getHolidays(): List<String> = runBlocking {
        withContext(Dispatchers.Default) { loadHolidays() }
    }

    // TODO - keep it for now as an option
    // data source https://www.nsw.gov.au/about-new-south-wales/public-holidays/
    private suspend fun loadHolidays(): List<String> {
        delay(3000)

        return listOf(
            "01/01/2019",
            "28/01/2019",
            "19/04/2019",
            "20/04/2019",
            "21/04/2019",
            "22/04/2019",
            "25/04/2019",
            "10/06/2019",
            "05/08/2019",
            "07/10/2019",
            "25/12/2019",
            "26/12/2019",

            "01/01/2020",
            "27/01/2020",
            "10/04/2020",
            "11/04/2020",
            "12/04/2020",
            "13/04/2020",
            "25/04/2020",
            "08/06/2020",
            "03/08/2020",
            "05/10/2020",
            "25/12/2020",
            "26/12/2020",
            "28/12/2020"
        )
    }
}