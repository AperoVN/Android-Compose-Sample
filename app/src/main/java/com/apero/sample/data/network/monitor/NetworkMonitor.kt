package com.apero.sample.data.network.monitor

import kotlinx.coroutines.flow.SharedFlow

/**
 * Utility for reporting app connectivity status
 */
interface NetworkMonitor {
    val isOnline: SharedFlow<Boolean>
}