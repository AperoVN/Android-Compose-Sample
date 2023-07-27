package com.apero.sample.data.remoteconfig

import kotlinx.coroutines.flow.Flow

/**
 * @author KO Huyn
 * @since 14/07/2023.
 */
interface RemoteConfigRepository {
    suspend fun sync(): Boolean
    fun getSpanCountHome(): Flow<Int?>
}