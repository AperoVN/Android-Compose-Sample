package com.apero.sample.di

import android.content.Context
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.analytics.FirebaseAnalyticsHelper
import com.apero.sample.analytics.ConsoleAnalyticsHelper
import com.apero.sample.di.qualifier.AnalyticsConsole
import com.apero.sample.di.qualifier.AnalyticsFirebase
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by KO Huyn on 25/07/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @Provides
    @Singleton
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
//    @AnalyticsFirebase
    fun provideFirebaseAnalyticsHelper(firebaseAnalytics: FirebaseAnalytics): AnalyticsHelper {
        return FirebaseAnalyticsHelper(firebaseAnalytics)
    }

//    @Provides
//    @AnalyticsConsole
//    fun provideConsoleAnalyticsHelper(): AnalyticsHelper {
//        return ConsoleAnalyticsHelper()
//    }
}