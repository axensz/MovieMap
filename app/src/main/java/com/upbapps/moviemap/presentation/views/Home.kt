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
import com.upbapps.moviemap.presentation.components.SerieItem
import com.upbapps.moviemap.presentation.methods.getTopRatedMovies
import com.upbapps.moviemap.presentation.methods.getTopRatedSeries
import com.upbapps.moviemap.presentation.methods.getTrendingTodayMovies
import com.upbapps.moviemap.presentation.methods.getUpcomingMovies

@Composable
fun Home(navController: NavHostController, movieViewModel: MovieViewModel) {
    var peliculasTop by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var seriesTop by remember { mutableStateOf<List<Serie>>(emptyList()) }
    var peliculasTendencia by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var peliculasProximas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loading = true
        getTrendingTodayMovies { lista -> peliculasTendencia = lista }
        getTopRatedMovies { lista -> peliculasTop = lista }
        getTopRatedSeries { lista -> seriesTop = lista }
        getUpcomingMovies { lista -> peliculasProximas = lista }
        loading = false
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
            Text("Inicio", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            when {
                loading -> {
                    CircularProgressIndicator()
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
                        Spacer(modifier = Modifier.height(10.dp))
                        CarruselMovie(peliculasTop, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Estrenos próximos", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(10.dp))
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
