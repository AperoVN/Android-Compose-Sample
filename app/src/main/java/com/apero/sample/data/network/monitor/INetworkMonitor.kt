package com.apero.sample.data.network.monitor

import kotlinx.coroutines.flow.Flow

/**
 * Utility for reporting app connectivity status
 */
interface INetworkMonitor {
    val isOnline: Flow<Boolean>
}
