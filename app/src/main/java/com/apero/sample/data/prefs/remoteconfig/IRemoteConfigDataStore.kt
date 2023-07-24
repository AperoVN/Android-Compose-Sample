package com.apero.sample.data.prefs.remoteconfig

import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn on 14/07/2023.
 */
interface IRemoteConfigDataStore {
    suspend fun setHomeSpanCount(spanCount: Int?)
    fun getHomeSpanCount(): Flow<Int?>
}