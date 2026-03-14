package com.takehomechallenge.adamyanuar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteEntity)

    @Delete
    suspend fun delete(character: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<FavoriteEntity>>
}
