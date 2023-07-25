package com.apero.sample.worker

import com.apero.sample.analytics.AnalyticsEvent
import com.apero.sample.analytics.AnalyticsHelper

fun AnalyticsHelper.logSyncStarted() =
    logEvent(
        AnalyticsEvent(type = "remote_config_sync_started"),
    )

fun AnalyticsHelper.logSyncFinished(syncedSuccessfully: Boolean) {
    val eventType =
        if (syncedSuccessfully) "remote_config_sync_successful" else "remote_config_sync_failed"
    logEvent(
        AnalyticsEvent(type = eventType),
    )
}
