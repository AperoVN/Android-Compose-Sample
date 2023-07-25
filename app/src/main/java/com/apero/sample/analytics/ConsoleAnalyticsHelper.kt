package com.apero.sample.analytics

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ConsoleAnalyticsHelper"

@Singleton
class ConsoleAnalyticsHelper @Inject constructor() : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        Log.d(TAG, "Received analytics event: $event")
    }
}
