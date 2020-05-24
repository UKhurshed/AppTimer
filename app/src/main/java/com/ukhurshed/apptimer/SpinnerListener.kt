package com.ukhurshed.apptimer

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi

class SpinnerListener(private val context: Context, private val stats: MutableList<String>, private val listView: ListView) :
    AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        stats.clear()
        stats.addAll(getAppUsages(context, position))
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, stats)
        listView.adapter = adapter
        listView.invalidate()
    }
}
