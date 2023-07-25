package com.apero.sample.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.data.remoteconfig.IRemoteConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Created by KO Huyn on 14/07/2023.
 */
@HiltWorker
class RemoteConfigSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val remoteConfig: IRemoteConfig,
    private val analyticsHelper: AnalyticsHelper
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        analyticsHelper.logSyncStarted()
        remoteConfig.sync().also { success ->
            analyticsHelper.logSyncFinished(success)
        }
        return Result.success()
    }
}