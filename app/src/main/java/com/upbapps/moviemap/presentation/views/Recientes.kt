package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upbapps.moviemap.presentation.methods.getRecentMovies
import com.upbapps.moviemap.presentation.models.Movie
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import com.upbapps.moviemap.presentation.methods.getRecentSeries
import com.upbapps.moviemap.presentation.models.Serie
import androidx.compose.ui.graphics.Color

@Composable
fun Recientes(navController: NavController){
    var peliculas by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var series by remember {mutableStateOf<List<Serie>>(emptyList())}
    var mostrarPeliculas by remember {mutableStateOf(true)}

    LaunchedEffect(Unit) {
        loading = true
        getRecentMovies { lista ->
            peliculas = lista
            loading = false
        }
        getRecentSeries { lista ->
            series = lista
            loading = false
        }
    }
    Column{
        Header(navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Agregadas Recientemente", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.height(40.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
                ) {
                    Button(
                        onClick = {mostrarPeliculas = true},
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (mostrarPeliculas) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (mostrarPeliculas) Color.White else MaterialTheme.colorScheme.primary
                        )
                    ){
                        Text("Películas")
                    }
                    Button(
                        onClick = {mostrarPeliculas = false},
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!mostrarPeliculas) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (!mostrarPeliculas) Color.White else MaterialTheme.colorScheme.primary
                        )
                    ){
                        Text("Series")
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.height(40.dp)
                ){
                    Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            when {
                loading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxHeight().weight(1f),
                        columns = GridCells.Fixed(2)
                    ) {
                        if(mostrarPeliculas){
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