package com.example.hiltproject.utils


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Logger {

    var isDebugMode = true
    var allowedTags = listOf("App", "Network", "Logger")
    var enableCrashlytics = true
    var enableLocalLogCache = true

    lateinit var appContext: Context

    fun init(context: Context, isDebug: Boolean = true) {
        appContext = context
        isDebugMode = isDebug
    }

    fun debug(tag: String, message: String) {
        if (isAllowed(tag)) Log.d(tag, message)
    }

    fun info(tag: String, message: String) {
        if (isAllowed(tag)) Log.i(tag, message)
    }

    fun warn(tag: String, message: String) {
        if (isAllowed(tag)) Log.w(tag, message)
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        if (isAllowed(tag)) Log.e(tag, message, throwable)
        if (enableCrashlytics && throwable != null) {
            FirebaseCrashlytics.getInstance().recordException(throwable)
        }
    }

    fun toast(message: String) {
        if (isDebugMode) Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isAllowed(tag: String): Boolean {
        return isDebugMode && allowedTags.contains(tag)
    }

    // --- Advanced Logging (Extendable / Placeholders) ---

    fun logImage(tag: String, description: String) {
        debug(tag, "[Image] $description")
    }

    fun logVector(tag: String, description: String) {
        debug(tag, "[Vector] $description")
    }

    fun logLoadingStart(tag: String, message: String = "Loading started") {
        info(tag, "[LoadingStart] $message")
    }

    fun logLoadingEnd(tag: String, message: String = "Loading ended") {
        info(tag, "[LoadingEnd] $message")
    }

    // --- Local Log DB Caching (to be implemented) ---

    fun cacheLogToLocal(tag: String, message: String, level: String = "DEBUG") {
        if (!enableLocalLogCache) return
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: Implement DB insert using Room (LogEntity)
        }
    }

    // --- Firebase / Server Sync Placeholder ---

    fun syncLogsToServer() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: Upload logs from DB to server or Firebase
        }
    }

    // --- Developer Tooling Support (to be implemented in UI) ---

    fun exportLogs(): String {
        // TODO: Return formatted logs for sharing/exporting
        return "Exported logs (to be implemented)"
    }

    fun filterLogsByTag(tag: String): List<String> {
        // TODO: Fetch logs from Room DB by tag
        return emptyList()
    }

    fun filterLogsByLevel(level: String): List<String> {
        // TODO: Fetch logs by level
        return emptyList()
    }
}
