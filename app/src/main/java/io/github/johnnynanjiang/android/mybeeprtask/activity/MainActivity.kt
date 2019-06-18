package io.github.johnnynanjiang.android.mybeeprtask.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.EditText
import io.github.johnnynanjiang.android.mybeeprtask.R
import io.github.johnnynanjiang.android.mybeeprtask.domain.DateHelper
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.DEFAULT_NUMBER_OF_BUSINESS_DAYS
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_BUSINESS_DAY_CALCULATION_ACTION
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_END_DATE
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_NUMBER_OF_BUSINESS_DAYS
import io.github.johnnynanjiang.android.mybeeprtask.service.BusinessDayCalculationService.Companion.KEY_START_DATE
import kotlinx.android.synthetic.main.activity_main.button_calculate
import kotlinx.android.synthetic.main.activity_main.date_from
import kotlinx.android.synthetic.main.activity_main.date_to
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var responseReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_calculate.setOnClickListener { runCalculator() }
        date_from.setOnClickListener { setDatePickerDialog(date_from) }
        date_to.setOnClickListener { setDatePickerDialog(date_to) }
        registerReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        deregisterReceiver()
    }

    private fun setDatePickerDialog(editText: EditText) =
        showDatePickerDialog(editText)

    private fun showDatePickerDialog(editText: EditText) {
        val today = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            editText.setText(DateHelper.getFormattedDateString(day, month + 1, year))
        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE))

        datePickerDialog.show()
    }

    private fun showAlertDialog(title: String, message: String) =
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(R.string.button_ok, null)
            .create()
            .show()

    private fun showInvalidInputError() = showAlertDialog(
        getString(R.string.error_title),
        getString(R.string.error_message)
    )

    private fun isInputValid(): Boolean {
        val from = date_from.text.toString()
        val to = date_to.text.toString()

        if (from.isNullOrEmpty() || to.isNullOrEmpty()) {
            return false
        }

        try {
            with(DateHelper) {
                getDateFromString(from)
                getDateFromString(to)
            }
        } catch (exception: Throwable) {
            return false
        }

        return true
    }

    private fun runCalculator() {
        if (!isInputValid()) {
            showInvalidInputError()
            return
        }

        val intent = Intent(this, BusinessDayCalculationService::class.java)
        val startDate = date_from.text.toString()
        val endDate = date_to.text.toString()
        intent.putExtra(KEY_START_DATE, startDate)
        intent.putExtra(KEY_END_DATE, endDate)

        startService(intent)

        Log.w(
            "MyBeeprTask",
            String.format(
                resources.getString(R.string.start_message),
                startDate,
                endDate
            )
        )
    }

    private fun registerReceiver() {
        responseReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    val startDate = intent.getStringExtra(KEY_START_DATE)
                    val endDate = intent.getStringExtra(KEY_END_DATE)
                    val numberOfBusinessDays =
                        intent.getIntExtra(KEY_NUMBER_OF_BUSINESS_DAYS, DEFAULT_NUMBER_OF_BUSINESS_DAYS)

                    showAlertDialog(
                        getString(R.string.info_title),
                        String.format(
                            resources.getString(R.string.result_message),
                            numberOfBusinessDays,
                            startDate,
                            endDate
                        )
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
