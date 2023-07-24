package com.apero.sample.data.remoteconfig

import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn on 14/07/2023.
 */
interface IRemoteConfig {
    suspend fun sync(): Boolean
    fun getSpanCountHome(): Flow<Int?>
}