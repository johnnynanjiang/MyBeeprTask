package io.github.johnnynanjiang.android.mybeeprtask.domain

import org.junit.Test
import junit.framework.Assert.*

class HolidayTest {
    @Test
    fun testFixedHolidays() {
        assertEquals("18/06/2019", FixedHoliday(18, 6).getDateInYear(2019))
    }

    @Test
    fun testFlexibleHolidays() {
        assertEquals("01/10/2018", FlexibleHoliday(30, 9).getDateInYear(2018))
        assertEquals("17/06/2019", FlexibleHoliday(15, 6).getDateInYear(2019))
        assertEquals("17/06/2019", FlexibleHoliday(16, 6).getDateInYear(2019))
        assertEquals("17/06/2019", FlexibleHoliday(17, 6).getDateInYear(2019))
    }

    @Test
    fun testEasterHolidays() {
        assertEquals("19/04/2019", GoodFridayHoliday().getDateInYear(2019))
        assertEquals("22/04/2019", EasterMondayHoliday().getDateInYear(2019))

        assertEquals("10/04/2020", GoodFridayHoliday().getDateInYear(2020))
        assertEquals("13/04/2020", EasterMondayHoliday().getDateInYear(2020))
    }

    @Test
    fun testQueensBirthdayHoliday() {
        assertEquals("10/06/2019", QueensBirthdayHoliday().getDateInYear(2019))
        assertEquals("08/06/2020", QueensBirthdayHoliday().getDateInYear(2020))
    }
}