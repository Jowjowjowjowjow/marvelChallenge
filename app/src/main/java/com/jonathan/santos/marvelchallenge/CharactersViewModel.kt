package com.jonathan.santos.marvelchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.santos.marvelchallenge.model.CharacterDataContainer
import com.jonathan.santos.marvelchallenge.repositories.CharactersRepository

class CharactersViewModel(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val _charactersRepositoryResponseLiveData = MutableLiveData<CharacterDataContainer>()
    val charactersRepositoryResponseLiveData: LiveData<CharacterDataContainer> =
        _charactersRepositoryResponseLiveData

    private val _errorGettingCharactersRepositoryResponseLiveData = MutableLiveData<Throwable>()
    val errorGettingCharactersRepositoryResponseLiveData: LiveData<Throwable> =
        _errorGettingCharactersRepositoryResponseLiveData

    suspend fun getCharacters(offset: Int) {
        val response = charactersRepository.getCharactersRepository(offset)
        if (response.isSuccessful) {
            _charactersRepositoryResponseLiveData.postValue(response.body()?.data)
        } else {
            _errorGettingCharactersRepositoryResponseLiveData.postValue(
                Throwable(
                    response.errorBody().toString()
                )
            )
        }
    }
}