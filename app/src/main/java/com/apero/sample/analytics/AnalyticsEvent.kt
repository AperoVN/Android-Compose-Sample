package com.apero.sample.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    // Standard analytics types.
    class Types {
        companion object {
            const val SCREEN_VIEW = "screen_view" // (extras: SCREEN_NAME)
        }
    }
    data class Param(val key: String, val value: String)

    // Standard parameter keys.
    class ParamKeys {
        companion object {
            const val SCREEN_NAME = "screen_name"
        }
    }
}
