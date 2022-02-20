package com.jonathan.santos.marvelchallenge.data.services

import com.jonathan.santos.marvelchallenge.model.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int? = 0,
        @Query("orderBy") orderBy: String? = "name"
    ): Response<CharacterDataWrapper>
}