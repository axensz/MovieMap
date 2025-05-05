package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upbapps.moviemap.presentation.methods.getMovies
import com.upbapps.moviemap.presentation.models.Movie
import androidx.compose.runtime.*
import java.nio.file.WatchEvent
import androidx.compose.foundation.lazy.items

@Composable
fun Home (navController: NavHostController){
    val peliculas = remember { mutableStateOf<List<Movie>>(emptyList()) }
    LaunchedEffect(Unit) {
        getMovies {
            lista -> peliculas.value = lista
        }
    }
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            items(peliculas.value) { movie ->
                Row(){
                    Text(text = movie.title)
                }
            }
        }
    }
    Text(text = "Home")
}