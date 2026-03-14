package com.takehomechallenge.adamyanuar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import android.content.Context

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "favorite_db"
            ).build()
        }
    }
}