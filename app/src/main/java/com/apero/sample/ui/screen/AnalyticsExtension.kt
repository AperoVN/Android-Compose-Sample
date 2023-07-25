package com.apero.sample.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.apero.sample.analytics.AnalyticsEvent
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.analytics.LocalAnalyticsHelper

/**
 * Created by KO Huyn on 25/07/2023.
 */

/**
 * Classes and functions associated with analytics events for the UI.
 */
fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(AnalyticsEvent.ParamKeys.SCREEN_NAME, screenName),
            ),
        ),
    )
}

/**
 * A side-effect which records a screen view event.
 */
@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
    onDispose {}
}

