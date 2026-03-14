package com.takehomechallenge.adamyanuar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.takehomechallenge.adamyanuar.data.local.AppDatabase
import com.takehomechallenge.adamyanuar.navigation.BottomBar
import com.takehomechallenge.adamyanuar.navigation.NavGraph
import com.takehomechallenge.adamyanuar.repository.CharacterRepository
import com.takehomechallenge.adamyanuar.viewmodel.CharacterViewModel
import com.takehomechallenge.adamyanuar.viewmodel.CharacterViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = AppDatabase.create(applicationContext)
            val repository = CharacterRepository(
                database.favoriteDao()
            )
            val factory = CharacterViewModelFactory(repository)
            val vm: CharacterViewModel = viewModel(factory = factory)
            MainScreen(vm)
        }
    }
}

@Composable
fun MainScreen(vm: CharacterViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            NavGraph(
                navController = navController,
                vm = vm
            )
        }
    }
}