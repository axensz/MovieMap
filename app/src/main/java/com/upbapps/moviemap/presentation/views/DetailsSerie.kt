package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upbapps.moviemap.presentation.components.Header
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import java.util.Locale

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun DetailsSerie(navController: NavController, serie: Serie, movieViewModel: MovieViewModel) {
    val genresNames = serie.listGenress.mapNotNull { genresSeriesMap[it] }
    var showDialog by remember { mutableStateOf(false) }
    Column {
        Header(navController)
        Box {
            AsyncImage(
                model = IMAGE_BASE_URL + serie.backdropPath,
                contentDescription = serie.name,
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(serie.name, style = MaterialTheme.typography.titleMedium)

                LazyRow {
                    items(genresNames) { genre ->
                        GenreSerieView(genre)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                Text(
                    serie.overview,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    serie.first_air_date,
                    modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    style = MaterialTheme.typography.bodyMedium
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
                        text = String.format(Locale.getDefault(), "%.1f", serie.voteAverage)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${serie.voteCount}) votos",
                        color = Color.LightGray
                    )
                }

                Button(
                    onClick = {
                        movieViewModel.addSerieToFavorites(serie)
                        showDialog = true
                    },
                    modifier = Modifier.padding(top = 20.dp)
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
fun GenreSerieView(genre: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

val genresSeriesMap = mapOf(
    10759 to "Acción y aventura",
    16 to "Animación",
    35 to "Comedia",
    80 to "Crimen",
    99 to "Documental",
    18 to "Drama",
    10751 to "Familia",
    10762 to "Niños",
    10763 to "Noticias",
    10764 to "Realidad",
    10765 to "Fantasía y Sci Fi",
    10770 to "Película TV",
    10766 to "Soap",x
    10637 to "Hablar",
    10738 to "Guerra y política",
    37 to "Western"
)