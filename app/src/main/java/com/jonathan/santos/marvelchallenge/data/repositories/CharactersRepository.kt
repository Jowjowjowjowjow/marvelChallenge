package com.jonathan.santos.marvelchallenge.data.repositories

import com.jonathan.santos.marvelchallenge.data.services.CharactersService
import com.jonathan.santos.marvelchallenge.model.CharacterDataWrapper
import retrofit2.Response

class CharactersRepository(private val charactersService: CharactersService) {

    suspend fun getCharactersRepository(offset: Int? = 0): Response<CharacterDataWrapper> =
        charactersService.getCharacters(offset)
}