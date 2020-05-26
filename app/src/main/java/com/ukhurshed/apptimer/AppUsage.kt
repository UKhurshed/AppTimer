package com.ukhurshed.apptimer

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun getAppUsages(context: Context, interval: Int): List<String> {
    val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val starOfDay = getStartOfDay()
    val currentTime = System.currentTimeMillis()

    val usageStatsList = manager.queryUsageStats(interval, starOfDay, currentTime)

    return usageStatsList
        .sortedByDescending { stat -> stat.totalTimeInForeground }
        .mapNotNull { stat -> getAppUsage(context, stat) }
}

fun getAppUsage(context: Context, usageStats: UsageStats): String? {
    return try {
        val info = context.packageManager.getApplicationInfo(usageStats.packageName, 0)
        val label = context.packageManager.getApplicationLabel(info)
        val time = usageStats.totalTimeInForeground / 1000.0 / 60 / 60
        if (abs(time - 0.0) < 0.1) null else
            "$label ${String.format("%.1f h", time)}"
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getStartOfDay(): Long {
    val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
}
