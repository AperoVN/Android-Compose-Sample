package com.apero.sample.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.apero.sample.data.db.model.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn.
 */
@Dao
interface FavouriteDao {
    @Query("SELECT * FROM MOVIE_TABLE")
    suspend fun getListMovie(): List<MovieEntity>

    @Query("SELECT * FROM MOVIE_TABLE")
    fun getListMovieAsFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: MovieEntity)

    @Delete
    suspend fun delete(history: MovieEntity)

    @Update
    suspend fun update(history: MovieEntity)

    @Query("SELECT * FROM MOVIE_TABLE WHERE id = :id")
    suspend fun findMovieById(id: Int): MovieEntity
}