package com.takehomechallenge.adamyanuar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.adamyanuar.data.local.FavoriteEntity
import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.repository.CharacterRepository
import com.takehomechallenge.adamyanuar.ui.state.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters =
        MutableStateFlow<UiState<List<Character>>>(UiState.Loading)
    val characters: StateFlow<UiState<List<Character>>> =
        _characters

    private val _isPaginationLoading = MutableStateFlow(false)
    val isPaginationLoading: StateFlow<Boolean> =
        _isPaginationLoading
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
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

    private var currentPage = 1
    private var totalPages = Int.MAX_VALUE
    private var isLoading = false
    init {
        loadCharacters()
    }

    fun loadCharacters() {
        if (isLoading) return
        if (currentPage > totalPages) return
        viewModelScope.launch {
            isLoading = true
            _isPaginationLoading.value = true

            try {
                delay(500)
                val response = repository.getCharacters(currentPage)
                totalPages = response.info.pages
                val currentList =
                    (_characters.value as? UiState.Success)?.data ?: emptyList()
                _characters.value =
                    UiState.Success(currentList + response.results)
                currentPage++
            } catch (e: Exception) {
                _characters.value =
                    UiState.Error(e.message ?: "Load Error")
            } finally {
                isLoading = false
                _isPaginationLoading.value = false
            }
        }
    }

    fun refreshCharacters() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPage = 1
            totalPages = Int.MAX_VALUE

            try {
                val response = repository.getCharacters(1)
                totalPages = response.info.pages
                _characters.value =
                    UiState.Success(response.results)
                currentPage = 2
            } catch (e: Exception) {
                _characters.value =
                    UiState.Error(e.message ?: "Refresh Error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun search(name: String) {
        viewModelScope.launch {
            _searchResult.value = UiState.Loading

            try {
                val result =
                    repository.searchCharacter(name)
                if (result.isEmpty()) {
                    _searchResult.value = UiState.Empty
                } else {
                    _searchResult.value =
                        UiState.Success(result)
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
    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val existing =
                favorites.value.find { it.id == character.id }
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