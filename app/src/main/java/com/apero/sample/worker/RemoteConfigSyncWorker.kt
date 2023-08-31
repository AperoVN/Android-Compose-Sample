package com.apero.sample.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.data.remoteconfig.RemoteConfigRepository
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by KO Huyn on 14/07/2023.
 */
class RemoteConfigSyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val remoteConfig: RemoteConfigRepository by inject()
    private val analyticsHelper: AnalyticsHelper by inject()
    override suspend fun doWork(): Result {
        analyticsHelper.logSyncStarted()
        remoteConfig.sync().also { success ->
            analyticsHelper.logSyncFinished(success)
        }
        return Result.success()
    }
}