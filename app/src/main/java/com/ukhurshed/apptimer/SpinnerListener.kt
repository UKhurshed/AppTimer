package com.ukhurshed.apptimer

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class SpinnerListener(private val context: Context, private val pieChart: PieChart) :
    AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val stats = getAppUsages(context, position)

        val entries = stats.map { stat -> PieEntry(stat.first, stat.second) }
        val pieDataSet = PieDataSet(entries, "apps")

        pieDataSet.sliceSpace = 3F
        pieDataSet.selectionShift = 5F
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(10F)
        pieData.setValueTextColor(Color.YELLOW)

        pieChart.data = pieData

        pieChart.notifyDataSetChanged()
        pieChart.invalidate()
    }
}
