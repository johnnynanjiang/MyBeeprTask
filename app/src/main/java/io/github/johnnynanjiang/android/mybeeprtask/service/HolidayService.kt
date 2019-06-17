package io.github.johnnynanjiang.android.mybeeprtask.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HolidayService {
    fun getHolidays(): List<String> = runBlocking {
        withContext(Dispatchers.Default) { loadHolidays() }
    }

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