package com.upbapps.moviemap.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.upbapps.moviemap.data.AuthManager
import com.upbapps.moviemap.data.FirebaseManager
import com.upbapps.moviemap.presentation.components.BottomBar
import com.upbapps.moviemap.presentation.components.BottomNavItem
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.presentation.viewmodels.MovieViewModel
import com.upbapps.moviemap.presentation.views.*
import com.upbapps.moviemap.ui.theme.MovieMapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Verificar inicialización de Firebase
        FirebaseManager.testConnection(
            onSuccess = {
                Toast.makeText(this, "Firebase inicializado correctamente", Toast.LENGTH_SHORT).show()
            },
            onError = { error ->
                Toast.makeText(this, "Error de inicialización: $error", Toast.LENGTH_LONG).show()
            }
        )

        setContent {
            val movieViewModel: MovieViewModel = viewModel()
            MovieMapTheme {
                Navigation(movieViewModel)
            }
        }
    }
}

@Composable
fun Navigation(movieViewModel: MovieViewModel) {
    val navController = rememberNavController()
    val currentUser = AuthManager.getCurrentUser()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Lista de rutas que no deben mostrar la barra inferior
    val authRoutes = listOf("login", "register")
    val showBottomBar = currentRoute !in authRoutes && currentUser != null

    Scaffold(
        bottomBar = { 
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(padding)
        ) {
            // Rutas autenticadas
            composable(BottomNavItem.Home.route) {
                if (currentUser != null) {
                    Home(navController, movieViewModel)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
            composable(BottomNavItem.Recientes.route) {
                if (currentUser != null) {
                    Recientes(navController, movieViewModel)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
            composable(BottomNavItem.Populares.route) {
                if (currentUser != null) {
                    Populares(navController, movieViewModel)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
            composable(BottomNavItem.Listas.route) {
                if (currentUser != null) {
                    Listas(navController, movieViewModel)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }

            // Rutas de autenticación
            composable("login") { Login(navController) }
            composable("register") { Register(navController) }
            composable("user") { 
                if (currentUser != null) {
                    User(navController)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }

            // Rutas de detalles
            composable(
                route = "details_movie/{movie}",
                arguments = listOf(navArgument("movie") { type = NavType.StringType })
            ) { backStackEntry ->
                if (currentUser != null) {
                    val movieJSON = backStackEntry.arguments?.getString("movie")
                    val movie = Gson().fromJson(movieJSON, Movie::class.java)
                    DetailsMovie(navController, movie, movieViewModel)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }

            composable(
                route = "details_serie/{serie}",
                arguments = listOf(navArgument("serie") { type = NavType.StringType })
            ) { backStackEntry ->
                if (currentUser != null) {
                    val serieJSON = backStackEntry.arguments?.getString("serie")
                    val serie = Gson().fromJson(serieJSON, Serie::class.java)
                    DetailsSerie(navController, serie)
                } else {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val movieViewModel: MovieViewModel = viewModel()
    MovieMapTheme {
        Navigation(movieViewModel)
    }
}


