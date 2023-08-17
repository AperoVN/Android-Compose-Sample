package com.apero.sample.di

import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.apero.core.coroutine.di.CoreCoroutineModule
import com.apero.core.mediapicker.di.CoreMediaPickerModule
import com.apero.core.mediastore.di.CoreMediaStoreModule
import com.apero.core.photocropper.di.CoreCropModule
import com.apero.sample.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KoinAdapterModule {

    @Singleton
    @Provides
    fun provideKoinApplication(
        @ApplicationContext
        context: Context,
    ): KoinApplication {
        return startKoin {
            androidContext(context)
            androidLogger(Level.INFO)
            modules(
                CoreCoroutineModule().module,
                CoreCropModule().module,
                CoreMediaPickerModule().module,
                CoreMediaStoreModule().module.also {
                    val qualifier = CoreMediaStoreModule.foregroundSyncNotificationBuilder
                    @Suppress("RemoveExplicitTypeArguments", "DEPRECATION")
                    it.single<Notification.Builder>(qualifier) {
                        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val channelId = qualifier.value

                            val nc = NotificationChannelCompat.Builder(
                                channelId,
                                NotificationManagerCompat.IMPORTANCE_MIN
                            )
                                .setName(channelId)
                                .build()
                            NotificationManagerCompat.from(context)
                                .createNotificationChannelsCompat(listOf(nc))

                            Notification.Builder(context, channelId)
                        } else {
                            Notification.Builder(context)
                        }

                        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    }
                },
            )
        }
    }
}
