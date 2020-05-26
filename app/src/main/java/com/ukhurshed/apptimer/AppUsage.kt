package com.ukhurshed.apptimer

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
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
        .map { stat -> stat.packageName }
        .mapNotNull { name -> getAppInfo(name, context) }
        .mapNotNull { info -> getAppLabel(info, context) }
        .mapIndexedNotNull { index, label -> getTime(index, label, usageStatsList) }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getStartOfDay(): Long {
    val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
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

fun getTime(index: Int, label: String, list: List<UsageStats>): String? {
    val time = list[index].totalTimeInForeground / 1000.0 / 60 / 60
    return if (abs(time - 0.0) < 0.1) null else
        "$label ${String.format("%.1f h", time)}"
}