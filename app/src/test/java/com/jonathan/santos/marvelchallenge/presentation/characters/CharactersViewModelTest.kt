package com.jonathan.santos.marvelchallenge.presentation.characters

import androidx.lifecycle.Observer
import com.jonathan.santos.marvelchallenge.data.repositories.CharactersRepository
import com.jonathan.santos.marvelchallenge.model.*
import com.jonathan.santos.marvelchallenge.presentation.InstantExecutorExtension
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(InstantExecutorExtension::class)
class CharactersViewModelTest {
    private lateinit var charactersRepositoryMock: CharactersRepository
    private lateinit var subject: CharactersViewModel

    private lateinit var charactersRepositoryFreshResponseLiveDataObserver: Observer<CharacterDataContainer>
    private lateinit var charactersRepositoryResponseLiveDataObserver: Observer<CharacterDataContainer>
    private lateinit var errorGettingCharactersRepositoryLiveDataObserver: Observer<Throwable>

    @BeforeEach
    fun beforeEach() {
        charactersRepositoryMock = mockk()
        charactersRepositoryFreshResponseLiveDataObserver =
            mockk { every { onChanged(any()) } just Runs }
        charactersRepositoryResponseLiveDataObserver =
            mockk { every { onChanged(any()) } just Runs }
        errorGettingCharactersRepositoryLiveDataObserver =
            mockk { every { onChanged(any()) } just Runs }

        subject = CharactersViewModel(
            charactersRepositoryMock
        ).apply {
            charactersRepositoryFreshResponseLiveData.observeForever(
                charactersRepositoryFreshResponseLiveDataObserver
            )
            charactersRepositoryResponseLiveData.observeForever(
                charactersRepositoryResponseLiveDataObserver
            )
            errorGettingCharactersRepositoryResponseLiveData.observeForever(
                errorGettingCharactersRepositoryLiveDataObserver
            )
        }
    }

    @Test
    fun `When calling getCharacters offset = 0 Should set value for charactersRepositoryNewResponseLiveData`() {
        val response = CharacterDataWrapper(CharacterDataContainer(listOf(Character(1,"a","b",
            CharacterThumbnail("c","d"), ComicsContainer(listOf()), SeriesContainer(listOf())
        ))))

        coEvery {
            charactersRepositoryMock.getCharactersRepository(any())
        } returns Response.success(response)
        runBlocking {
            subject.getCharacters(0)
        }

        verify {
            charactersRepositoryFreshResponseLiveDataObserver.onChanged(any())
        }
        assertEquals(response.data, subject.charactersRepositoryFreshResponseLiveData.value)
    }

    @Test
    fun `When calling getCharacters offset != 0 Should set value for charactersRepositoryResponseLiveData`() {
        val response = CharacterDataWrapper(CharacterDataContainer(listOf(Character(1,"a","b",
            CharacterThumbnail("c","d"), ComicsContainer(listOf()), SeriesContainer(listOf())
        ))))

        coEvery {
            charactersRepositoryMock.getCharactersRepository(any())
        } returns Response.success(response)

        runBlocking {
            subject.getCharacters(20)
        }

        verify {
            charactersRepositoryResponseLiveDataObserver.onChanged(any())
        }
        assertEquals(response.data, subject.charactersRepositoryResponseLiveData.value)
    }

    @Test
    fun `When fail calling getCharacters Should emit value on error errorGettingCharactersRepositoryResponseLiveData`() {
        val response = Response.error<CharacterDataWrapper>(400, ResponseBody.create(MediaType.get("application/json"), "a"))

        coEvery {
            charactersRepositoryMock.getCharactersRepository(any())
        } returns response

        runBlocking {
            subject.getCharacters()
        }

        verify {
            errorGettingCharactersRepositoryLiveDataObserver.onChanged(any())
        }

    }
}

