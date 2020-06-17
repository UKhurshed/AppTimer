package com.ukhurshed.apptimer.Activities

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.ukhurshed.apptimer.Fragments.BarChartFragment
import com.ukhurshed.apptimer.Fragments.FragmentPieChart
import com.ukhurshed.apptimer.R
import kotlinx.android.synthetic.main.activity_main.*
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

        val pieChartFragment = FragmentPieChart()
        val barChartFragment = BarChartFragment()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.pieChart -> makeCurrentFragment(pieChartFragment)
                R.id.barChart -> makeCurrentFragment(barChartFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHome, fragment)
            commit()
        }
    }


}

