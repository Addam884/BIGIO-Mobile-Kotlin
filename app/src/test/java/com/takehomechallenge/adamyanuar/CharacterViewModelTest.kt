package com.takehomechallenge.adamyanuar.viewmodel

import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.data.model.CharacterResponse
import com.takehomechallenge.adamyanuar.repository.CharacterRepository
import com.takehomechallenge.adamyanuar.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import com.takehomechallenge.adamyanuar.MainDispatcherRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var repository: CharacterRepository
    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {
        repository = mock(CharacterRepository::class.java)
        `when`(repository.getFavorites())
            .thenReturn(flowOf(emptyList()))
        viewModel = CharacterViewModel(repository)
    }

    @Test
    fun loadCharacters_success() = runTest {
        val characters = listOf(
            Character(
                id = 1,
                name = "Rick",
                species = "Human",
                gender = "Male",
                image = "",
                origin = mock(),
                location = mock()
            )
        )
        `when`(repository.getCharacters(1))
            .thenReturn(
                CharacterResponse(
                    info = mock(),
                    results = characters
                )
            )
        viewModel.loadCharacters()
        advanceUntilIdle()
        val state = viewModel.characters.value
        assertTrue(state is UiState.Success)
    }

    @Test
    fun searchCharacter_success() = runTest {
        val characters = listOf(
            Character(
                id = 1,
                name = "Rick",
                species = "Human",
                gender = "Male",
                image = "",
                origin = mock(),
                location = mock()
            )
        )
        `when`(repository.searchCharacter("Rick"))
            .thenReturn(characters)
        viewModel.search("Rick")
        val state = viewModel.searchResult.value
        assertTrue(state is UiState.Success)
    }
    @Test
    fun searchCharacter_empty() = runTest {
        `when`(repository.searchCharacter("abc"))
            .thenReturn(emptyList())
        viewModel.search("abc")
        val state = viewModel.searchResult.value
        assertTrue(state is UiState.Empty)
    }
}