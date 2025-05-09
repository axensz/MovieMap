package com.upbapps.moviemap.presentation.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.upbapps.moviemap.data.AuthManager
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore
    val favoriteMovies = mutableStateListOf<Movie>()
    val favoriteSeries = mutableStateListOf<Serie>()
    private var lastUserId: String? = null

    init {
        observeAuthChanges()
    }

    private fun observeAuthChanges() {
        viewModelScope.launch {
            AuthManager.authState.collectLatest { user ->
                val userId = user?.uid
                if (userId != null && userId != lastUserId) {
                    lastUserId = userId
                    loadFavorites()
                } else if (userId == null) {
                    favoriteMovies.clear()
                    favoriteSeries.clear()
                    lastUserId = null
                }
            }
        }
    }

    private fun loadFavorites() {
        val userId = AuthManager.getCurrentUser()?.uid ?: return
        
        // Cargar películas favoritas
        db.collection("users").document(userId)
            .collection("favoriteMovies")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    favoriteMovies.clear()
                    for (doc in it.documents) {
                        doc.toObject(Movie::class.java)?.let { movie ->
                            favoriteMovies.add(movie)
                        }
                    }
                }
            }

        // Cargar series favoritas
        db.collection("users").document(userId)
            .collection("favoriteSeries")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    favoriteSeries.clear()
                    for (doc in it.documents) {
                        doc.toObject(Serie::class.java)?.let { serie ->
                            favoriteSeries.add(serie)
                        }
                    }
                }
            }
    }

    fun addMovieToFavorites(movie: Movie) {
        val userId = AuthManager.getCurrentUser()?.uid ?: return
        
        db.collection("users").document(userId)
            .collection("favoriteMovies")
            .document(movie.id.toString())
            .set(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) {
        val userId = AuthManager.getCurrentUser()?.uid ?: return
        
        db.collection("users").document(userId)
            .collection("favoriteMovies")
            .document(movie.id.toString())
            .delete()
    }

    fun addSerieToFavorites(serie: Serie) {
        val userId = AuthManager.getCurrentUser()?.uid ?: return
        
        db.collection("users").document(userId)
            .collection("favoriteSeries")
            .document(serie.id.toString())
            .set(serie)
    }

    fun removeSerieFromFavorites(serie: Serie) {
        val userId = AuthManager.getCurrentUser()?.uid ?: return
        
        db.collection("users").document(userId)
            .collection("favoriteSeries")
            .document(serie.id.toString())
            .delete()
    }

    fun isMovieFavorite(movieId: Int): Boolean {
        return favoriteMovies.any { it.id == movieId }
    }

    fun isSerieFavorite(serieId: Int): Boolean {
        return favoriteSeries.any { it.id == serieId }
    }
}


