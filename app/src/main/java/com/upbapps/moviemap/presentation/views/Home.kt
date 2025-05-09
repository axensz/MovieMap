package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upbapps.moviemap.presentation.components.Header
import com.upbapps.moviemap.presentation.components.MovieItem
import com.upbapps.moviemap.presentation.methods.getMovies
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import com.upbapps.moviemap.presentation.components.SerieItem
import com.upbapps.moviemap.presentation.methods.getTopRatedMovies
import com.upbapps.moviemap.presentation.methods.getTopRatedSeries
import com.upbapps.moviemap.presentation.methods.getTrendingTodayMovies
import com.upbapps.moviemap.presentation.methods.getUpcomingMovies
import kotlinx.coroutines.delay

@Composable
fun Home(navController: NavHostController, movieViewModel: MovieViewModel) {
    var peliculasTop by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var seriesTop by remember { mutableStateOf<List<Serie>>(emptyList()) }
    var peliculasTendencia by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var peliculasProximas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var loadingProgress by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        if (peliculasTop.isEmpty() && seriesTop.isEmpty() &&
            peliculasTendencia.isEmpty() && peliculasProximas.isEmpty()) {

            loading = true
            loadingProgress = 0
            try{
                getTrendingTodayMovies { lista ->
                    peliculasTendencia = lista
                    loadingProgress += 25
                }
                getTopRatedMovies { lista ->
                    peliculasTop = lista
                    loadingProgress += 25
                }
                getTopRatedSeries { lista ->
                    seriesTop = lista
                    loadingProgress += 25
                }
                getUpcomingMovies { lista ->
                    peliculasProximas = lista
                    loadingProgress += 25
                }
            }finally {
                delay(500)
                loading = false
            }
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
            Spacer(modifier = Modifier.height(16.dp))
            when {
                loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 6.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Cargando contenido...",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = loadingProgress / 100f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "$loadingProgress%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                error != null -> {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    Column (
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                    ){
                        Text("Películas en tendencia", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        CarruselMovie(peliculasTendencia, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Series mejor calificadas", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(10.dp))
                        CarruselSerie(seriesTop, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Películas mejor calificadas", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        CarruselMovie(peliculasTop, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Estrenos próximos", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        CarruselMovie(peliculasProximas, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CarruselMovie(movies: List<Movie>, navController: NavHostController){
    LazyRow (
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies){
            movie -> MovieItem(navController, movie)
        }
    }
}
@Composable
fun CarruselSerie(series: List<Serie>, navController: NavHostController){
    LazyRow (
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(series){
                movie -> SerieItem(navController, movie)
        }
    }
}
