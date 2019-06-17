package io.github.johnnynanjiang.android.mybeeprtask.service

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import io.github.johnnynanjiang.android.mybeeprtask.domain.BusinessDayCalculator

class BusinessDayCalculationService : IntentService("BusinessDayCalculationService") {
    companion object {
        const val KEY_NUMBER_OF_BUSINESS_DAYS = "numberOfBusinessDays"
        const val KEY_START_DATE = "startDate"
        const val KEY_END_DATE = "endDate"
        const val KEY_BUSINESS_DAY_CALCULATION_ACTION = "businessDayCalculation"
        const val DEFAULT_NUMBER_OF_BUSINESS_DAYS = 0
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val startDate = it.getStringExtra(KEY_START_DATE)
            val endDate = it.getStringExtra(KEY_END_DATE)
            val calculator = BusinessDayCalculator()
            val holidays = HolidayService().getHolidays()
            val numberOfBusinessDays = calculator.getNumberOfBusinessDaysBetween(startDate, endDate, holidays)

            intent.action = KEY_BUSINESS_DAY_CALCULATION_ACTION
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(intent.putExtra(KEY_NUMBER_OF_BUSINESS_DAYS, numberOfBusinessDays))
        }
    }
}