package com.upbapps.moviemap.presentation.views

import com.upbapps.moviemap.presentation.components.Header
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import java.util.Locale

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun DetailsMovie(
    navController: NavController,
    movie: Movie,
    movieViewModel: MovieViewModel
) {
    val genresNames = movie.listGenress.mapNotNull { genresMap[it] }
    var showDialog by remember { mutableStateOf(false) }
    Column {
        Header(navController)
        Box {
            AsyncImage(
                model = IMAGE_BASE_URL + movie.backdropPath,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(36.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(movie.title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)

                LazyRow {
                    items(genresNames) { genre ->
                        GenreView(genre)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                Text(
                    movie.overview,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    movie.releaseDate,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f", movie.voteAverage),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${movie.voteCount}) votos",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                // ✅ Botón para agregar a Listas
                Button(
                    onClick = {
                        movieViewModel.addMovieToFavorites(movie)
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar a Listas")
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Agregado") },
                        text = { Text("Se agregó a favoritos.") },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GenreView(genre: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

val genresMap = mapOf(
    28 to "Acción",
    12 to "Aventura",
    16 to "Animación",
    35 to "Comedia",
    80 to "Crimen",
    99 to "Documental",
    18 to "Drama",
    10751 to "Familia",
    14 to "Fantasía",
    36 to "Historia",
    27 to "Terror",
    10402 to "Musical",
    9648 to "Misterio",
    10749 to "Romance",
    878 to "Ciencia Ficción",
    10770 to "Película TV",
    53 to "Thriller",
    10752 to "Guerra",
    37 to "Western"
)