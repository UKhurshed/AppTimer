package com.ukhurshed.apptimer

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.Q)
fun getAppUsages(context: Context, interval: Int): List<String> {
    val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val currentTime = System.currentTimeMillis()
    val delta = 60 * 60 * 1000 * 24 *
            when (interval) {
                UsageStatsManager.INTERVAL_DAILY -> 1
                UsageStatsManager.INTERVAL_WEEKLY -> 7
                UsageStatsManager.INTERVAL_MONTHLY -> 7 * 31
                else -> throw IllegalArgumentException()
            }
    val usageStatsList = manager.queryUsageStats(interval, currentTime - delta, currentTime)

    return usageStatsList
        .map { stat -> stat.packageName }
        .mapNotNull { name -> getAppInfo(name, context) }
        .mapNotNull { info -> getAppLabel(info, context) }
        .mapIndexedNotNull { index, label ->  getTime(index, label, usageStatsList) }
}

fun getAppInfo(name: String, context: Context): ApplicationInfo? {
    return try {
        context.packageManager.getApplicationInfo(name, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

fun getAppLabel(info: ApplicationInfo, context: Context): String? {
    return try {
        context.packageManager.getApplicationLabel(info).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

fun getTime(index: Int, label: String, list: List<UsageStats>) : String? {
    val time = list[index].totalTimeInForeground / 1000.0 / 60 / 60
    return if (abs(time - 0.0) < 0.1)  null else
        "$label ${String.format("%.1f", time)}"
}