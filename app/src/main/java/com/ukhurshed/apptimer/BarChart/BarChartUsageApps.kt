package com.ukhurshed.apptimer.BarChart
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
fun getAppUsages(context: Context, interval: Int): List<Pair<Float, String>> {
    val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val starOfDay = getStartOfDay()
    val currentTime = System.currentTimeMillis()

    val usageStatsList = manager.queryUsageStats(interval, starOfDay, currentTime)

    return usageStatsList
        .sortedByDescending { stat -> stat.totalTimeInForeground }
        .mapNotNull { stat ->
            getAppUsage(
                context,
                stat
            )
        }
}

private fun getAppUsage(context: Context, usageStats: UsageStats): Pair<Float, String>? {
    return try {
        val info = context.packageManager.getApplicationInfo(usageStats.packageName, 0)
        val label = context.packageManager.getApplicationLabel(info).toString()
        val time = (usageStats.totalTimeInForeground / 1000.0 / 60).toFloat()
        if (abs(time - 0.0) < 0.1) null else
            time to label
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getStartOfDay(): Long {
    val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
}
