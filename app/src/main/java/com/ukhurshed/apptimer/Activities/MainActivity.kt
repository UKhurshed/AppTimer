package com.ukhurshed.apptimer.Activities

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.ukhurshed.apptimer.R
import com.ukhurshed.apptimer.SpinnerListener
import kotlinx.android.synthetic.main.main_toolbar.*

class MainActivity : AppCompatActivity() {
    lateinit var pieChart: PieChart

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        setSupportActionBar(toolbar)

        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        if (mode != AppOpsManager.MODE_ALLOWED) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createPie()

        createPeriodSelector()
    }

    private fun createPeriodSelector() {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = listOf("day", "week", "month")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = SpinnerListener(
            this,
            findViewById(R.id.pieChart)
        )
    }

    private fun createPie() {
        pieChart = findViewById(R.id.pieChart)
        pieChart.setExtraOffsets(5F, 10F, 5F, 5F)

        pieChart.dragDecelerationFrictionCoef = 0.95F

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61F
    }
}

