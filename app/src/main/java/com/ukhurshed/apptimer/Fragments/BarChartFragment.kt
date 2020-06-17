package com.ukhurshed.apptimer.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.ukhurshed.apptimer.PieChart.BarChartSpinnerListener
import com.ukhurshed.apptimer.PieChart.PieChartSpinnerListener
import com.ukhurshed.apptimer.R

class BarChartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bar_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getContext = context

//        val barChart: BarChart = view.findViewById(R.id.barChart)
//        barChart.setExtraOffsets(5F, 10F, 5F, 5F)

//        barChart.dragDecelerationFrictionCoef = 0.95F

//        barChart.isDrawHoleEnabled = true
//        barChart.setHoleColor(Color.WHITE)
//        barChart.transparentCircleRadius = 61F


        val spinner = view.findViewById<Spinner>(R.id.BarChartSpinner)
        val items = listOf("day", "week", "month")
        val spinnerAdapter = ArrayAdapter(getContext!! ,android.R.layout.simple_dropdown_item_1line, items)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = BarChartSpinnerListener(getContext!!, view.findViewById(R.id.barChart))
    }

    companion object {
        @JvmStatic
        fun instance(): BarChartFragment{
            return BarChartFragment()
        }
    }
}