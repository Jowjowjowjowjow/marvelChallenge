package com.jonathan.santos.marvelchallenge.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.santos.marvelchallenge.data.repositories.CharactersRepository
import com.jonathan.santos.marvelchallenge.model.CharacterDataContainer

class CharactersViewModel(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val _charactersRepositoryNewResponseLiveData = MutableLiveData<CharacterDataContainer>()
    val charactersRepositoryNewResponseLiveData: LiveData<CharacterDataContainer> =
        _charactersRepositoryNewResponseLiveData

    private val _charactersRepositoryResponseLiveData = MutableLiveData<CharacterDataContainer>()
    val charactersRepositoryResponseLiveData: LiveData<CharacterDataContainer> =
        _charactersRepositoryResponseLiveData

    private val _errorGettingCharactersRepositoryResponseLiveData = MutableLiveData<Throwable>()
    val errorGettingCharactersRepositoryResponseLiveData: LiveData<Throwable> =
        _errorGettingCharactersRepositoryResponseLiveData

    suspend fun getCharacters(offset: Int? = 0) {
        val response = charactersRepository.getCharactersRepository(offset)
        if (response.isSuccessful) {
            if (offset == 0) {
                _charactersRepositoryNewResponseLiveData.postValue(response.body()?.data)
            } else {
                _charactersRepositoryResponseLiveData.postValue(response.body()?.data)
            }
        } else {
            _errorGettingCharactersRepositoryResponseLiveData.postValue(
                Throwable(
                    response.errorBody().toString()
                )
            )
        }
    }
}