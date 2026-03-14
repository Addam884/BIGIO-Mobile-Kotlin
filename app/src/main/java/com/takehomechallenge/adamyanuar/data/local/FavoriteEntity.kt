package com.takehomechallenge.adamyanuar.data.local

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "favorite")
data class FavoriteEntity(

    @PrimaryKey
    val id: Int,

    val name: String,
    val species: String,
    val gender: String,
    val image: String
)