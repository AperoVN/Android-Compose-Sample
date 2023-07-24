package com.apero.sample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apero.sample.data.db.converter.TypeConverter
import com.apero.sample.data.db.dao.FavouriteDao
import com.apero.sample.data.db.model.MovieEntity

/**
 * Created by KO Huyn.
 */

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): FavouriteDao
}