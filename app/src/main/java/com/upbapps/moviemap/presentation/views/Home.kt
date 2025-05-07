package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upbapps.moviemap.presentation.methods.getMovies
import com.upbapps.moviemap.presentation.models.Movie

@Composable
fun Home(navController: NavHostController) {
    var peliculas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loading = true
        getMovies { lista ->
            peliculas = lista
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Películas Populares", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            loading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    columns = GridCells.Fixed(2)
                ) {
                    items(peliculas) { movie ->
                        MovieSerieItem(movie)
                    }
                }
            }
        }
    }
}