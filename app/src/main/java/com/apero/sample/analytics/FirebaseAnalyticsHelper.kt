package com.apero.sample.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by KO Huyn on 25/07/2023.
 */
@Singleton
class FirebaseAnalyticsHelper @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) :
    AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(
            event.type,
            bundleOf(*event.extras.map { it.key to it.value }.toTypedArray())
        )
    }
}