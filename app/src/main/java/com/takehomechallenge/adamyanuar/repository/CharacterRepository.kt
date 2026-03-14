package com.takehomechallenge.adamyanuar.repository

import com.takehomechallenge.adamyanuar.data.api.RetrofitClient
import com.takehomechallenge.adamyanuar.data.local.FavoriteDao
import com.takehomechallenge.adamyanuar.data.local.FavoriteEntity
import com.takehomechallenge.adamyanuar.data.model.Character

class CharacterRepository(
    private val dao: FavoriteDao
) {

    suspend fun getCharacters() =
        RetrofitClient.api.getCharacters().results

    suspend fun searchCharacter(name: String) =
        RetrofitClient.api.searchCharacter(name).results

    suspend fun getCharacterById(id: Int): Character =
        RetrofitClient.api.getCharacterById(id)
    suspend fun addFavorite(character: FavoriteEntity) =
        dao.insert(character)

    suspend fun removeFavorite(character: FavoriteEntity) =
        dao.delete(character)

    fun getFavorites() =
        dao.getFavorites()
}
