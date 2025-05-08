package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.components.Header
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun Listas(navController: NavController, movieViewModel: MovieViewModel) {
    val favoriteMovies = movieViewModel.favoriteMovies
    Column{
        Header(navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Películas Guardadas", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (favoriteMovies.isEmpty()) {
                Text("No has agregado películas aún.")
            } else {
                LazyColumn {
                    items(favoriteMovies) { movie ->
                        MovieListItem(movie = movie) {
                            // Al hacer clic, navegamos a detalles
                            val movieJson = Gson().toJson(movie)
                            navController.navigate("details_movie/${movieJson}")
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
