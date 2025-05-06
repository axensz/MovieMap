package com.upbapps.moviemap.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upbapps.moviemap.presentation.views.*
import com.upbapps.moviemap.ui.theme.MovieMapTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = BottomNavItem.Home.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(BottomNavItem.Home.route) { HomeScreen() }
                    composable(BottomNavItem.Recientes.route) { RecientesScreen() }
                    composable(BottomNavItem.Populares.route) { PopularesScreen() }
                    composable(BottomNavItem.Listas.route) { ListasScreen() }
                }
            }
        }
    }
}


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("login") { Login(navController) }
        composable("register") {Register(navController)}
        composable("home") { Home(navController) }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MovieMapTheme {
        Navigation()
    }
}

