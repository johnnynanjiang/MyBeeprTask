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
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_ACTION
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_END_DATE
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_NUMBER_OF_BUSINESS_DAYS
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_START_DATE
import kotlinx.android.synthetic.main.activity_main.button_calculate

class MainActivity : AppCompatActivity() {
    private lateinit var responseReceiver: ResponseReceiver

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

    fun runCalculator() {
        val intent = Intent(this, BusinessDayCalculationService::class.java)
        intent.putExtra(KEY_START_DATE, "17/06/2019")
        intent.putExtra(KEY_END_DATE, "17/06/20099")
        startService(intent)
    }

    private fun registerReceiver() {
        responseReceiver = ResponseReceiver()
        val intentFilter = IntentFilter()
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, intentFilter)
    }

    private fun deregisterReceiver() {
        unregisterReceiver(responseReceiver)
    }

    private class ResponseReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val numberOfBusinessDays = intent.getIntExtra(KEY_NUMBER_OF_BUSINESS_DAYS, DEFAULT_NUMBER_OF_BUSINESS_DAYS)
            }
        }
    }
}
