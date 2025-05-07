package com.upbapps.moviemap.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            composable("login") {Login(navController)}
            composable("registro") {Register(navController)}
            composable(BottomNavItem.Home.route) { Home(navController) }
            composable(BottomNavItem.Recientes.route) { Recientes(navController) }
            composable(BottomNavItem.Populares.route) { Populares(navController) }
            composable(BottomNavItem.Listas.route) { Listas(navController) }
            composable("details"){Details(navController)}
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

