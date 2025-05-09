package com.upbapps.moviemap.presentation

import android.os.Bundle
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
        
        // Verificar inicialización de Firebase silenciosamente
        FirebaseManager.testConnection(
            onSuccess = { },
            onError = { }
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
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val authState by AuthManager.authState.collectAsState()

    // Solo mostrar la barra inferior en las rutas principales
    val showBottomBar = when (currentRoute) {
        "login", "register" -> false
        else -> true
    }

    // Efecto para manejar la navegación basada en el estado de autenticación
    LaunchedEffect(authState) {
        when {
            authState != null && currentRoute == "login" -> {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            authState == null && currentRoute != "login" && currentRoute != "register" -> {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        bottomBar = { 
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = if (authState != null) "home" else "login",
            modifier = Modifier.padding(padding)
        ) {
            // Rutas autenticadas
            composable(BottomNavItem.Home.route) {
                Home(navController, movieViewModel)
            }
            composable(BottomNavItem.Recientes.route) {
                Recientes(navController, movieViewModel)
            }

            composable(BottomNavItem.Populares.route) {
                Populares(navController, movieViewModel)
            }
            composable(BottomNavItem.Listas.route) {
                Lists(navController, movieViewModel)
            }

            // Rutas de autenticación
            composable("login") { Login(navController) }
            composable("register") { Register(navController) }
            composable("user") { User(navController) }

            // Rutas de detalles
            composable(
                route = "details_movie/{movie}",
                arguments = listOf(navArgument("movie") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieJSON = backStackEntry.arguments?.getString("movie")
                val movie = Gson().fromJson(movieJSON, Movie::class.java)
                DetailsMovie(navController, movie, movieViewModel)
            }

            composable(
                route = "details_serie/{serie}",
                arguments = listOf(navArgument("serie") { type = NavType.StringType })
            ) { backStackEntry ->
                val serieJSON = backStackEntry.arguments?.getString("serie")
                val serie = Gson().fromJson(serieJSON, Serie::class.java)
                DetailsSerie(navController, serie, movieViewModel)
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


