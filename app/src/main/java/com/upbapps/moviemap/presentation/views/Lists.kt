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

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun Lists(navController: NavController, movieViewModel: MovieViewModel) {
    var showMovies by remember { mutableStateOf(true) }
    
    Column {
        Header(navController)
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
                    Text("Películas")
                }
                Button(
                    onClick = { showMovies = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!showMovies) MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text("Series")
                }
            }

            if (showMovies) {
                if (movieViewModel.favoriteMovies.isEmpty()) {
                    Text("No has agregado películas aún.")
                } else {
                    LazyColumn {
                        items(movieViewModel.favoriteMovies) { movie ->
                            MovieListItem(movie = movie) {
                                val movieJson = Gson().toJson(movie)
                                navController.navigate("details_movie/${movieJson}")
                            }
                        }
                    }
                }
            } else {
                if (movieViewModel.favoriteSeries.isEmpty()) {
                    Text("No has agregado series aún.")
                } else {
                    LazyColumn {
                        items(movieViewModel.favoriteSeries) { serie ->
                            SerieListItem(serie = serie) {
                                val serieJson = Gson().toJson(serie)
                                navController.navigate("details_serie/${serieJson}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL + movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(movie.title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SerieListItem(serie: Serie, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL + serie.posterPath,
            contentDescription = serie.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(serie.name, style = MaterialTheme.typography.bodyLarge)
    }
} 