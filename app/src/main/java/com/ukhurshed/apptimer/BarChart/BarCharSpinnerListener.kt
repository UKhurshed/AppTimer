package com.ukhurshed.apptimer.PieChart

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class BarChartSpinnerListener(private val context: Context, private val barChart: BarChart) :
    AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val stats = getAppUsages(context, position)

        val entries = stats.map { stat -> BarEntry(stat.first, stat.second.toFloat()) }
        val barDataSet = BarDataSet(entries, "apps")

        val barData = BarData(barDataSet)
        barChart.setFitBars(true)
        barDataSet.setValueTextColor(Color.YELLOW)
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barChart.data = barData
        barChart.description.text = "Bar Char"
        barChart.animateY(2000)

        barChart.notifyDataSetChanged()
        barChart.invalidate()
    }
}
