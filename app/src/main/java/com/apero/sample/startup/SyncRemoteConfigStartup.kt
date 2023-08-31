package com.apero.sample.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.apero.sample.worker.RemoteConfigSyncWorker

/**
 * Created by KO Huyn on 31/08/2023.
 */
class SyncRemoteConfigStartup : Initializer<Unit> {
    override fun create(context: Context) {
        val worker = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<RemoteConfigSyncWorker>()
            .setConstraints(constraints)
            .build()
        worker.enqueue(workRequest)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(KoinStartup::class.java, WorkManagerInitializer::class.java)
    }
}