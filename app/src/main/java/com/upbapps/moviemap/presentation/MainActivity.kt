package com.upbapps.moviemap.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.upbapps.moviemap.ui.theme.MovieMapTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upbapps.moviemap.presentation.vistas.Home
import com.upbapps.moviemap.presentation.vistas.Login

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieMapTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login"){
        composable("login") { Login(navController) }
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