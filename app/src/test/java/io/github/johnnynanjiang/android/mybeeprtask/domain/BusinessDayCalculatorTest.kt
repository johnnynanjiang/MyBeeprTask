package io.github.johnnynanjiang.android.mybeeprtask.domain

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class BusinessDayCalculatorTest {
    private val calculator = BusinessDayCalculator()

    @Test
    fun getDateFromString() {
        val date = "17/06/2019"

        with(calculator) {
            assertEquals(2019, getDateFromString(date).year + 1900)
            assertEquals(Calendar.JUNE, getDateFromString(date).month)
            assertEquals(17, getDateFromString(date).date)
        }
    }

    @Test
    fun getNumberOfBusinessDaysWithoutHolidays() {
        with(calculator) {
            assertEquals(0, getNumberOfBusinessDaysBetween("17/06/2019", "17/06/2019"))
            assertEquals(0, getNumberOfBusinessDaysBetween("17/06/2019", "18/06/2019"))
            assertEquals(1, getNumberOfBusinessDaysBetween("17/06/2019", "19/06/2019"))
            assertEquals(2, getNumberOfBusinessDaysBetween("17/06/2019", "20/06/2019"))
            assertEquals(3, getNumberOfBusinessDaysBetween("17/06/2019", "21/06/2019"))
            assertEquals(4, getNumberOfBusinessDaysBetween("17/06/2019", "22/06/2019"))
            assertEquals(4, getNumberOfBusinessDaysBetween("17/06/2019", "23/06/2019"))
            assertEquals(4, getNumberOfBusinessDaysBetween("17/06/2019", "24/06/2019"))
            assertEquals(5, getNumberOfBusinessDaysBetween("17/06/2019", "25/06/2019"))
        }
    }

    @Test
    fun getNumberOfBusinessDaysWithHolidays() {
        val holidays = listOf("18/06/2019", "20/06/2019", "24/06/2019")

        with(calculator) {
            assertEquals(0, getNumberOfBusinessDaysBetween("17/06/2019", "19/06/2019", holidays))
            assertEquals(1, getNumberOfBusinessDaysBetween("17/06/2019", "21/06/2019", holidays))
            assertEquals(2, getNumberOfBusinessDaysBetween("17/06/2019", "25/06/2019", holidays))
        }
    }

    @Test
    fun myBeeprSpec() {
        with(calculator) {
            assertEquals(1, getNumberOfBusinessDaysBetween("07/08/2014", "11/08/2014"))
            assertEquals(5, getNumberOfBusinessDaysBetween("13/08/2014", "21/08/2014"))
        }
    }
}