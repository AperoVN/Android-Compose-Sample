package com.apero.sample.data.repository

import com.apero.sample.analytics.AnalyticsEvent
import com.apero.sample.analytics.AnalyticsHelper

/**
 * Created by KO Huyn on 25/07/2023.
 */

fun AnalyticsHelper.logMovieApi(page: Int?) {
    this.logEvent(
        AnalyticsEvent(
            type = "api_movie_popular",
            extras = listOf(AnalyticsEvent.Param("page", page?.toString() ?: "N/A"))
        )
    )
}