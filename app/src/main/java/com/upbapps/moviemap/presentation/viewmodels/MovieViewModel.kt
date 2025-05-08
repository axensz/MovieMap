package com.upbapps.moviemap.presentation.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.upbapps.moviemap.presentation.models.Movie

class MovieViewModel : ViewModel() {
    // Lista de películas agregadas a “Listas”
    val favoriteMovies = mutableStateListOf<Movie>()

    fun addToList(movie: Movie) {
        // Evitar duplicados usando el ID
        if (!favoriteMovies.any { it.id == movie.id }) {
            favoriteMovies.add(movie)
        }
    }

    fun removeFromList(movie: Movie) {
        favoriteMovies.removeIf { it.id == movie.id }
    }
}


