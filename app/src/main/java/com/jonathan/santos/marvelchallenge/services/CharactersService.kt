package com.jonathan.santos.marvelchallenge.services

import com.jonathan.santos.marvelchallenge.data.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {
    @GET("characters")
    suspend fun getCharacters(@Query("offset") offset: Int? = 0): Response<CharacterDataWrapper>
}