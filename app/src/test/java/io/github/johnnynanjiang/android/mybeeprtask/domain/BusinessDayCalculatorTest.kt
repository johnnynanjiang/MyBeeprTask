package io.github.johnnynanjiang.android.mybeeprtask.domain

import org.junit.Test

import org.junit.Assert.*

class BusinessDayCalculatorTest {

    @Test
    fun getDateFromString() {
        val calculator = BusinessDayCalculator()
        val date = "17/06/2019"

        assertEquals(2019, calculator.getDateFromString(date).year + 1900)
        assertEquals(6, calculator.getDateFromString(date).month + 1)
        assertEquals(17, calculator.getDateFromString(date).date)
    }

    @Test
    fun getWeekDay() {
        val calculator = BusinessDayCalculator()

        assertEquals(1, calculator.getWeekDayOf("17/06/2019"))
        assertEquals(6, calculator.getWeekDayOf("22/06/2019"))
        assertEquals(0, calculator.getWeekDayOf("23/06/2019"))
        assertEquals(1, calculator.getWeekDayOf("24/06/2019"))
    }

    @Test
    fun getNumberOfWeekDays() {
        val calculator = BusinessDayCalculator()

        assertEquals(0, calculator.getNumberOfWeekDaysBetween("17/06/2019", "17/06/2019"))
        assertEquals(0, calculator.getNumberOfWeekDaysBetween("17/06/2019", "18/06/2019"))
        assertEquals(1, calculator.getNumberOfWeekDaysBetween("17/06/2019", "19/06/2019"))
        assertEquals(2, calculator.getNumberOfWeekDaysBetween("17/06/2019", "20/06/2019"))
        assertEquals(3, calculator.getNumberOfWeekDaysBetween("17/06/2019", "21/06/2019"))
        assertEquals(4, calculator.getNumberOfWeekDaysBetween("17/06/2019", "22/06/2019"))
        assertEquals(4, calculator.getNumberOfWeekDaysBetween("17/06/2019", "23/06/2019"))
        assertEquals(4, calculator.getNumberOfWeekDaysBetween("17/06/2019", "24/06/2019"))
        assertEquals(5, calculator.getNumberOfWeekDaysBetween("17/06/2019", "25/06/2019"))
    }

    @Test
    fun myBeeprSpec() {
        val calculator = BusinessDayCalculator()

        assertEquals(1, calculator.getNumberOfWeekDaysBetween("07/08/2014", "11/08/2014"))
        assertEquals(5, calculator.getNumberOfWeekDaysBetween("13/08/2014", "21/08/2014"))
    }
}