package com.apero.sample

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.apero.sample.worker.RemoteConfigSyncWorker
import dagger.hilt.android.HiltAndroidApp
import org.koin.core.KoinApplication
import javax.inject.Inject

/**
 * Created by KO Huyn.
 */
@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()
        syncingRemoteConfig()
    }

    private fun syncingRemoteConfig() {
        val worker = WorkManager.getInstance(this)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<RemoteConfigSyncWorker>()
            .setConstraints(constraints)
            .build()
        worker.enqueue(workRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}
