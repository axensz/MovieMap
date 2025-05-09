package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.upbapps.moviemap.presentation.methods.getFilteredMovies
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel

@Composable
fun Populares(navController: NavHostController, viewModel: MovieViewModel) {
    var peliculas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var series by remember { mutableStateOf<List<Serie>>(emptyList()) }
    var peliculasFiltradas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var seriesFiltradas by remember { mutableStateOf<List<Serie>>(emptyList()) }
    var mostrarPeliculas by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Filtros
    var showFilterDialog by remember { mutableStateOf(false) }
    var filtroYear by remember { mutableStateOf("") }
    var filtroRating by remember { mutableStateOf("") }
    var filtroGenre by remember { mutableStateOf("") }
    var expandedGenre by remember { mutableStateOf(false) }

    val generos = mapOf(
        28 to "Acción", 35 to "Comedia", 18 to "Drama", 27 to "Terror",
        10749 to "Romance", 16 to "Animación", 878 to "Ciencia Ficción"
    )

    LaunchedEffect(Unit) {
        if (peliculas.isEmpty() && series.isEmpty()) {
            loading = true
            try {
                getPopularMovies { lista ->
                    peliculas = lista
                    peliculasFiltradas = lista
                }
                getPopularSeries { lista ->
                    series = lista
                    seriesFiltradas = lista
                }
            } finally {
                loading = false
            }
        }
    }

    fun aplicarFiltrosPeliculas() {
        loading = true
        getFilteredMovies(
            year = filtroYear,
            rating = filtroRating,
            genre = filtroGenre,
            mediaType = "movie",
            onMovieResult = { lista -> // Especificar el callback para películas
                peliculasFiltradas = lista
                loading = false
            },
            onSerieResult = {} // Necesitas proporcionar un lambda para onSerieResult aunque esté vacío
        )
    }

    fun aplicarFiltrosSeries() {
        loading = true
        getFilteredMovies(
            year = filtroYear,
            rating = filtroRating,
            genre = filtroGenre,
            mediaType = "tv",
            onMovieResult = {}, // Necesitas proporcionar un lambda para onMovieResult aunque esté vacío
            onSerieResult = { lista -> // Especificar el callback para series
                seriesFiltradas = lista
                loading = false
            }
        )
    }

    fun aplicarFiltrosGeneral() {
        loading = true
        if (mostrarPeliculas) {
            aplicarFiltrosPeliculas()
        } else {
            aplicarFiltrosSeries()
        }
    }

    Column {
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
                OutlinedButton(
                    onClick = { showFilterDialog = true },
                    modifier = Modifier.height(40.dp)
                ) {
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
                            items(peliculasFiltradas) { movie ->
                                MovieItem(navController, movie)
                            }
                        } else {
                            items(seriesFiltradas) { serie ->
                                SerieItem(navController, serie)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filtros") },
            text = {
                Column {
                    OutlinedTextField(
                        value = filtroYear,
                        onValueChange = { filtroYear = it },
                        label = { Text("Año de estreno") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = filtroRating,
                        onValueChange = { filtroRating = it },
                        label = { Text("Calificación (1 a 10)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box {
                        OutlinedTextField(
                            value = generos[filtroGenre.toIntOrNull()] ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Género") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedGenre = true }
                        )
                        DropdownMenu(
                            expanded = expandedGenre,
                            onDismissRequest = { expandedGenre = false }
                        ) {
                            generos.forEach { (id, nombre) ->
                                DropdownMenuItem(
                                    text = { Text(nombre) },
                                    onClick = {
                                        filtroGenre = id.toString()
                                        expandedGenre = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        aplicarFiltrosGeneral()
                        showFilterDialog = false
                    }
                ) {
                    Text("Aplicar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showFilterDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
