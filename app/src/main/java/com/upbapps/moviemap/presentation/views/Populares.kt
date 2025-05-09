package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upbapps.moviemap.presentation.components.Header
import com.upbapps.moviemap.presentation.components.MovieItem
import com.upbapps.moviemap.presentation.components.SerieItem
import com.upbapps.moviemap.presentation.methods.getPopularMovies
import com.upbapps.moviemap.presentation.methods.getPopularSeries
import com.upbapps.moviemap.presentation.methods.getRecentMovies
import com.upbapps.moviemap.presentation.methods.getRecentSeries
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel

@Composable
fun Populares(navController: NavHostController, viewModel: MovieViewModel) {
    var peliculas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var series by remember { mutableStateOf<List<Serie>>(emptyList()) }
    var mostrarPeliculas by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if(peliculas.isEmpty() && series.isEmpty()){
            loading = true
            try {
                getPopularMovies { lista -> peliculas = lista }
                getPopularSeries { lista -> series = lista }
            } finally {
                loading = false
            }
        }
    }

    Column {
        Header(navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Populares", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
                ) {
                    Button(
                        onClick = { mostrarPeliculas = true },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (mostrarPeliculas) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (mostrarPeliculas) Color.White else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Películas")
                    }
                    Button(
                        onClick = { mostrarPeliculas = false },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!mostrarPeliculas) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (!mostrarPeliculas) Color.White else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Series")
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(onClick = {}, modifier = Modifier.height(40.dp)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {
                loading -> CircularProgressIndicator()
                error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        columns = GridCells.Fixed(2)
                    ) {
                        if (mostrarPeliculas) {
                            items(peliculas) { movie ->
                                MovieItem(navController, movie)
                            }
                        } else {
                            items(series) { serie ->
                                SerieItem(navController, serie)
                            }
                        }
                    }
                }
            }
        }
    }
}

