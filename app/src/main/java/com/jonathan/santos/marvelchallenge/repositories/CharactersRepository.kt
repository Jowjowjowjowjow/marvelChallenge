package com.jonathan.santos.marvelchallenge.repositories

import com.jonathan.santos.marvelchallenge.data.CharacterDataWrapper
import com.jonathan.santos.marvelchallenge.services.CharactersService
import retrofit2.Response

class CharactersRepository(private val charactersService: CharactersService) {

    suspend fun getCharactersRepository(offset: Int?): Response<CharacterDataWrapper> = charactersService.getCharacters(offset)
}