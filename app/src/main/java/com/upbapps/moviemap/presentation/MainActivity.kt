package com.upbapps.moviemap.presentation

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.views.*
import com.upbapps.moviemap.ui.theme.MovieMapTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(BottomNavItem.Home.route) { Home(navController) }
            composable(BottomNavItem.Recientes.route) { Recientes(navController) }
            composable(BottomNavItem.Populares.route) { Populares(navController) }
            composable(BottomNavItem.Listas.route) { Listas(navController) }

            composable("login") {Login(navController)}
            composable("registro") {Register(navController)}
            composable("user"){User(navController)}
            composable(
                route="details/{movie}",
                arguments = listOf(navArgument("movie") { type = NavType.StringType })
            ){ backStackEntry ->
                val movieJSON = backStackEntry.arguments?.getString("movie")
                val movie = Gson().fromJson(movieJSON, Movie::class.java)
                Details(navController, movie)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MovieMapTheme {
        Navigation()
    }
}

