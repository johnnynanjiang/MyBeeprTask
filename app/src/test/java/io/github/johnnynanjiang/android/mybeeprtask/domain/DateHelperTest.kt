package io.github.johnnynanjiang.android.mybeeprtask.domain

import org.junit.Test
import java.util.*

import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getDate
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getStringFromDate
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getDateFromString
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.isWeekend
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper.Companion.getYear
import junit.framework.Assert.*

class DateHelperTest {
    @Test
    fun getDateFromString() {
        val date = "17/06/2019"

        assertEquals(2019, getDateFromString(date).year + 1900)
        assertEquals(java.util.Calendar.JUNE, getDateFromString(date).month)
        assertEquals(17, getDateFromString(date).date)
    }

    @Test
    fun getStringFromDate() {
        val cal = Calendar.getInstance()
        cal.set(2019, 5, 17)
        val date = cal.time

        assertEquals("17/06/2019", getStringFromDate(date))
    }

    @Test
    fun isWeekend() {
        assertTrue(isWeekend(getDate(16, 6, 2019)))
        assertFalse(isWeekend(getDate(18, 6, 2019)))
    }

    @Test
    fun getYear() {
        assertEquals(2019, getYear("01/01/2019"))
        assertEquals(2020, getYear("01/01/2020"))
    }
}