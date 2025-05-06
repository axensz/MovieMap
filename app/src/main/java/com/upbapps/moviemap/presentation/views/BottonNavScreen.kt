package com.upbapps.moviemap.presentation.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector

// 1. Pantallas (vistas)
@Composable
fun HomeScreen() {
    Text("Inicio")
}

@Composable
fun RecientesScreen() {
    Text("Recientes")
}

@Composable
fun PopularesScreen() {
    Text("Populares")
}

@Composable
fun ListasScreen() {
    Text("Listas")
}

// 2. Ítems de la barra de navegación
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Inicio")
    object Recientes : BottomNavItem("recientes", Icons.Filled.History, "Recientes")
    object Populares : BottomNavItem("populares", Icons.Filled.Star, "Populares")
    object Listas : BottomNavItem("listas", Icons.Filled.List, "Listas")
}

// 3. Componente de la barra inferior
@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Recientes,
        BottomNavItem.Populares,
        BottomNavItem.Listas
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


