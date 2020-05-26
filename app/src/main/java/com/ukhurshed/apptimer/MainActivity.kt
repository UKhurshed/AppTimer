package com.ukhurshed.apptimer

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        if (mode != AppOpsManager.MODE_ALLOWED){
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stats = mutableListOf<String>()
        createPeriodSelector(stats)
        createAppUsagesView(stats)
    }

    private fun createPeriodSelector(stats: MutableList<String>) {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = listOf("day", "week", "month")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = SpinnerListener(this, stats, findViewById(R.id.listView))
    }

    private fun createAppUsagesView(stats: MutableList<String>) {
        val listView = findViewById<ListView>(R.id.listView)

        val listViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stats)
        listView.adapter = listViewAdapter
    }
}

