package io.github.johnnynanjiang.android.mybeeprtask.service

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HolidayServiceTest {
    @Test
    fun testGetHolidays() {
        runBlocking {
            assertEquals(25, HolidayService().getHolidays().size)
        }
    }
}