package com.takehomechallenge.adamyanuar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.adamyanuar.data.local.FavoriteEntity
import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.repository.CharacterRepository
import com.takehomechallenge.adamyanuar.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters =
        MutableStateFlow<UiState<List<Character>>>(UiState.Loading)
    val characters: StateFlow<UiState<List<Character>>> =
        _characters
    private val _searchResult =
        MutableStateFlow<UiState<List<Character>>>(UiState.Empty)
    val searchResult: StateFlow<UiState<List<Character>>> =
        _searchResult
    val favorites = repository.getFavorites()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun loadCharacters() {
        viewModelScope.launch {
            _characters.value = UiState.Loading
            try {
                val result = repository.getCharacters()
                if (result.isEmpty()) {
                    _characters.value = UiState.Empty
                } else {
                    _characters.value = UiState.Success(result)
                }
            } catch (e: Exception) {
                _characters.value =
                    UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            _searchResult.value = UiState.Loading
            try {
                val result = repository.searchCharacter(name)
                if (result.isEmpty()) {
                    _searchResult.value = UiState.Empty
                } else {
                    _searchResult.value = UiState.Success(result)
                }
            } catch (e: Exception) {
                _searchResult.value =
                    UiState.Error(e.message ?: "Search Error")
            }
        }
    }

    suspend fun getCharacterDetail(id: Int): Character {
        return repository.getCharacterById(id)
    }
    fun isFavorite(id: Int): Boolean {
        return favorites.value.any { it.id == id }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val existing = favorites.value.find { it.id == character.id }
            if (existing != null) {
                repository.removeFavorite(existing)
            } else {
                repository.addFavorite(
                    FavoriteEntity(
                        character.id,
                        character.name,
                        character.species,
                        character.gender,
                        character.image
                    )
                )
            }
        }
    }

    fun removeFavorite(character: FavoriteEntity) {
        viewModelScope.launch {
            repository.removeFavorite(character)
        }
    }
}