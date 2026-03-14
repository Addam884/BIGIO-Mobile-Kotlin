package com.takehomechallenge.adamyanuar.data.api

import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("character")
    suspend fun searchCharacter(
        @Query("name") name: String
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character
}