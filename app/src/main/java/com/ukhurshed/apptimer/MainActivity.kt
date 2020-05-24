package com.ukhurshed.apptimer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)

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

