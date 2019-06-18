package io.github.johnnynanjiang.android.mybeeprtask.service

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HolidayServiceTest {
    @Test
    fun getHolidaysInYear() {
        val holidaysIn2019 = HolidayService().getHolidaysInYear(2019)

        assertEquals(10, holidaysIn2019.size)
        assertEquals("25/04/2019", holidaysIn2019[0])
        assertEquals("10/06/2019", holidaysIn2019[9])
    }

    @Test
    fun getHolidaysInYears() {
        val holidays = HolidayService().getHolidaysInYears(2019, 2020)

        assertEquals(20, holidays.size)
        assertEquals("25/04/2019", holidays[0])
        assertEquals("10/06/2019", holidays[9])
        assertEquals("25/04/2020", holidays[10])
        assertEquals("08/06/2020", holidays[19])
    }

    @Test
    fun getHolidays() {
        runBlocking {
            assertEquals(25, HolidayService().getHolidays().size)
        }
    }
}