package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import androidx.compose.ui.graphics.Color

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun DetailsMovie(
    navController: NavController,
    movie: Movie,
    movieViewModel: MovieViewModel
) {
    val genres_names = movie.list_genres.mapNotNull { genresMap[it] }

    Column {
        Header(navController)
        Box(){
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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)

                LazyRow {
                    items(genres_names) { genre ->
                        genreView(genre)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                Text(
                    movie.overview,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    movie.releaseDate,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                // ✅ Botón para agregar a Listas
                Button(
                    onClick = {
                        movieViewModel.addToList(movie)
                        println("Película agregada a la lista: ${movie.title}")
                    },
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text("Agregar a Listas")
                }
            }
        }
    }
}

@Composable
fun genreView(genre: String) {
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
