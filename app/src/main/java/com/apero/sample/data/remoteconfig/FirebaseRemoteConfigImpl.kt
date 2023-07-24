package com.apero.sample.data.remoteconfig

import android.util.Log
import androidx.annotation.WorkerThread
import com.apero.sample.data.prefs.remoteconfig.IRemoteConfigDataStore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

/**
 * Created by KO Huyn on 14/07/2023.
 */
class FirebaseRemoteConfigImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    private val remoteConfigDataStore: IRemoteConfigDataStore
) : IRemoteConfig {

    @WorkerThread
    override suspend fun sync(): Boolean {
        try {
            val isSuccess = remoteConfig.fetchAndActivate().await()
            if (isSuccess) {
                remoteConfig[FirebaseRemoteConfigKey.SPAN_COUNT_HOME].asLong().let {
                    remoteConfigDataStore.setHomeSpanCount(it.toInt())
                }
            }
            Log.d(
                "FirebaseRemoteConfigImpl",
                "data: ${remoteConfig.all}"
            )
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun getSpanCountHome(): Flow<Int?> {
        return remoteConfigDataStore.getHomeSpanCount()
    }
}