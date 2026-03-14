package com.takehomechallenge.adamyanuar.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.ui.screen.*
import com.takehomechallenge.adamyanuar.ui.state.UiState
import com.takehomechallenge.adamyanuar.ui.theme.Accent
import com.takehomechallenge.adamyanuar.ui.theme.BgDark
import com.takehomechallenge.adamyanuar.ui.theme.TextMuted
import com.takehomechallenge.adamyanuar.viewmodel.CharacterViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    vm: CharacterViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            val state by vm.characters.collectAsState()

            LaunchedEffect(state) {
                if (state is UiState.Empty) {
                    vm.loadCharacters()
                }
            }

            val favoriteEntities by vm.favorites.collectAsState(initial = emptyList())
            val favorites = favoriteEntities.map { it.id }
            val isLoadingMore by vm.isPaginationLoading.collectAsState()
            val isRefreshing by vm.isRefreshing.collectAsState()
            when (state) {
                is UiState.Loading -> LoadingState()
                is UiState.Success -> {
                    val characters =
                        (state as UiState.Success<List<Character>>).data
                    HomeScreen(
                        characters = characters,
                        favorites = favorites,
                        isLoadingMore = isLoadingMore,
                        isRefreshing = isRefreshing,
                        onClick = {
                            navController.navigate("detail/${it.id}")
                        },
                        onFavorite = {
                            vm.toggleFavorite(it)
                        },
                        onLoadMore = {
                            vm.loadCharacters()
                        },
                        onRefresh = {
                            vm.refreshCharacters()
                        }
                    )
                }
                is UiState.Empty ->
                    MessageState("No characters found")
                is UiState.Error ->
                    MessageState((state as UiState.Error).message)
            }
        }

        composable("search") {
            val state by vm.searchResult.collectAsState()
            SearchScreen(
                results = when (state) {
                    is UiState.Success -> (state as UiState.Success).data
                    else -> emptyList()
                },
                onSearch = { vm.search(it) },
                onClick = { navController.navigate("detail/${it.id}") }
            )
        }

        composable("favorite") {
            val favorites by vm.favorites.collectAsState(initial = emptyList())
            FavoriteScreen(
                favorites = favorites,
                onRemove = { vm.removeFavorite(it) },
                onItemClick = {
                    navController.navigate("detail/${it.id}")
                }
            )
        }

        composable(
            "detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId")
            val favoriteEntities by vm.favorites.collectAsState(initial = emptyList())
            var character by remember { mutableStateOf<Character?>(null) }
            LaunchedEffect(id) {
                if (id != null) {
                    character = vm.getCharacterDetail(id)
                }
            }
            character?.let {
                val isFavorite = favoriteEntities.any { fav ->
                    fav.id == it.id
                }
                DetailScreen(
                    character = it,
                    isFavoriteInitial = isFavorite,
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = { vm.toggleFavorite(it) }
                )
            } ?: LoadingState()
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Accent)
    }
}

@Composable
private fun MessageState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "⊘",
                fontSize = 48.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = message,
                color = TextMuted,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}