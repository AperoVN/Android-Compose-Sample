package com.apero.sample.di

import android.app.Application
import androidx.room.Room
import com.apero.sample.R
import com.apero.sample.data.db.AppDatabase
import com.apero.sample.data.db.dao.FavouriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by KO Huyn.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "${application.getString(R.string.app_name)}.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: AppDatabase): FavouriteDao {
        return database.getHistoryDao()
    }
}