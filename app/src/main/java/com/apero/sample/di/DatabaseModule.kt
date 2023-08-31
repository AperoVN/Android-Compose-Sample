package com.apero.sample.di

import android.content.Context
import androidx.room.Room
import com.apero.sample.R
import com.apero.sample.data.db.AppDatabase
import org.koin.dsl.module

/**
 * Created by KO Huyn.
 */

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "${get<Context>().getString(R.string.app_name)}.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().getHistoryDao()
    }
}