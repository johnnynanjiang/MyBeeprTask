package io.github.johnnynanjiang.android.mybeeprtask.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import io.github.johnnynanjiang.android.mybeeprtask.R
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.DEFAULT_NUMBER_OF_BUSINESS_DAYS
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_BUSINESS_DAY_CALCULATION_ACTION
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_END_DATE
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_NUMBER_OF_BUSINESS_DAYS
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_START_DATE
import kotlinx.android.synthetic.main.activity_main.button_calculate
import kotlinx.android.synthetic.main.activity_main.text_hint

class MainActivity : AppCompatActivity() {
    private lateinit var responseReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_calculate.setOnClickListener { runCalculator() }
        registerReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        deregisterReceiver()
    }

    private fun runCalculator() {
        val intent = Intent(this, BusinessDayCalculationService::class.java)
        val startDate = "17/06/2019"
        val endDate = "17/06/2020"
        intent.putExtra(KEY_START_DATE, startDate)
        intent.putExtra(KEY_END_DATE, endDate)
        text_hint.text = String.format(
            resources.getString(R.string.start_message),
            startDate,
            endDate
        )
        startService(intent)
    }

    private fun registerReceiver() {
        responseReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    val startDate = intent.getStringExtra(KEY_START_DATE)
                    val endDate = intent.getStringExtra(KEY_END_DATE)
                    val numberOfBusinessDays =
                        intent.getIntExtra(KEY_NUMBER_OF_BUSINESS_DAYS, DEFAULT_NUMBER_OF_BUSINESS_DAYS)
                    text_hint.text = String.format(
                        resources.getString(R.string.result_message),
                        numberOfBusinessDays,
                        startDate,
                        endDate
                    )
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(KEY_BUSINESS_DAY_CALCULATION_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, intentFilter)
    }

    private fun deregisterReceiver() {
        unregisterReceiver(responseReceiver)
    }
}
