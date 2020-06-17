package com.ukhurshed.apptimer.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.ukhurshed.apptimer.R
import com.ukhurshed.apptimer.PieChart.PieChartSpinnerListener

class FragmentPieChart: Fragment() {

    companion object{
        fun instance(): FragmentPieChart{
            return FragmentPieChart()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pie_chart_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContext = context

        val pieChart: PieChart = view.findViewById(R.id.pieChart)
        pieChart.setExtraOffsets(5F, 10F, 5F, 5F)

        pieChart.dragDecelerationFrictionCoef = 0.95F

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61F
        pieChart.centerText="Apps Usage"


        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val items = listOf("day", "week", "month")
        val spinnerAdapter = ArrayAdapter(getContext!! ,android.R.layout.simple_dropdown_item_1line, items)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener =
            PieChartSpinnerListener(
                getContext!!, view.findViewById(R.id.pieChart)
            )
    }


}