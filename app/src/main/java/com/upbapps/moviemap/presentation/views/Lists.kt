package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.components.Header
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import androidx.compose.ui.res.painterResource
import com.upbapps.moviemap.R
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import android.net.Uri

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun Lists(navController: NavController, movieViewModel: MovieViewModel) {
    var showMovies by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar && snackbarMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(snackbarMessage)
            showSnackbar = false
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Selector de tipo de contenido
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showMovies = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showMovies) MaterialTheme.colorScheme.primary 
                            else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text("Películas", style = MaterialTheme.typography.labelLarge)
                    }
                    Button(
                        onClick = { showMovies = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!showMovies) MaterialTheme.colorScheme.primary 
                            else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text("Series", style = MaterialTheme.typography.labelLarge)
                    }
                }

                if (showMovies) {
                    if (movieViewModel.favoriteMovies.isEmpty()) {
                        Text("No has agregado películas aún.", style = MaterialTheme.typography.bodyLarge)
                    } else {
                        LazyColumn {
                            items(movieViewModel.favoriteMovies) { movie ->
                                MovieListItem(
                                    movie = movie,
                                    onClick = {
                                        val movieJson = Uri.encode(Gson().toJson(movie))
                                        navController.navigate("details_movie/${movieJson}")
                                    },
                                    onRemove = {
                                        movieViewModel.removeMovieFromFavorites(movie)
                                        snackbarMessage = "Película eliminada de favoritos."
                                        showSnackbar = true
                                    }
                                )
                            }
                        }
                    }
                } else {
                    if (movieViewModel.favoriteSeries.isEmpty()) {
                        Text("No has agregado series aún.", style = MaterialTheme.typography.bodyLarge)
                    } else {
                        LazyColumn {
                            items(movieViewModel.favoriteSeries) { serie ->
                                SerieListItem(
                                    serie = serie,
                                    onClick = {
                                        val serieJson = Uri.encode(Gson().toJson(serie))
                                        navController.navigate("details_serie/${serieJson}")
                                    },
                                    onRemove = {
                                        movieViewModel.removeSerieFromFavorites(serie)
                                        snackbarMessage = "Serie eliminada de favoritos."
                                        showSnackbar = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit, onRemove: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL + movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Detalles")
        }
        IconButton(onClick = onRemove) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
        }
    }
}

@Composable
fun SerieListItem(serie: Serie, onClick: () -> Unit, onRemove: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL + serie.posterPath,
            contentDescription = serie.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(serie.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Detalles")
        }
        IconButton(onClick = onRemove) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
        }
    }
} 