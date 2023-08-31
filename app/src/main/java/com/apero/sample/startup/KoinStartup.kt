package com.apero.sample.startup

import android.content.Context
import androidx.startup.Initializer
import com.apero.sample.di.analyticsModule
import com.apero.sample.di.applicationModule
import com.apero.sample.di.coroutineModule
import com.apero.sample.di.dataStoreModule
import com.apero.sample.di.databaseModule
import com.apero.sample.di.mediaProviderModule
import com.apero.sample.di.networkModule
import com.apero.sample.di.remoteConfigModule
import com.apero.sample.di.repositoryModule
import com.apero.sample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * Created by KO Huyn on 31/08/2023.
 */
class KoinStartup : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(
                analyticsModule +
                        applicationModule +
                        coroutineModule +
                        databaseModule +
                        dataStoreModule +
                        mediaProviderModule +
                        networkModule +
                        remoteConfigModule +
                        repositoryModule +
                        viewModelModule
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}