package com.apero.sample.data.remoteconfig

import android.util.Log
import androidx.annotation.WorkerThread
import com.apero.sample.data.prefs.remoteconfig.IRemoteConfigDataStore
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

/**
 * @author KO Huyn
 * @since 14/07/2023.
 */
class FirebaseRemoteConfigRepository(
    private val crashlytics: FirebaseCrashlytics,
    private val remoteConfig: FirebaseRemoteConfig,
    private val remoteConfigDataStore: IRemoteConfigDataStore
) : RemoteConfigRepository {
    @WorkerThread
    override suspend fun sync(): Boolean {
        return try {
            val isSuccess = remoteConfig.fetchAndActivate().await()
            if (isSuccess) {
                remoteConfig[FirebaseRemoteConfigKey.SPAN_COUNT_HOME].asLong().let {
                    remoteConfigDataStore.setHomeSpanCount(it.toInt())
                }
            }
            Log.d(
                TAG,
                "data: ${remoteConfig.all}"
            )
            true
        } catch (e: Exception) {
            crashlytics.recordException(e)
            Log.e(TAG, "sync failed with error: ", e)
            false
        }
    }

    override fun getSpanCountHome(): Flow<Int?> {
        return remoteConfigDataStore.getHomeSpanCount()
    }

    companion object {
        private const val TAG = "FirebaseRemoteConfigImpl"
    }
}