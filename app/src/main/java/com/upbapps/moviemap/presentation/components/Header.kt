package com.upbapps.moviemap.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upbapps.moviemap.R

@Composable
fun Header(navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.width(80.dp).clickable { navController.navigate("home") }
        )
        Text(text = "MovieMap",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { navController.navigate("home") }
        )
        IconButton(
            onClick = {navController.navigate("user"){
                launchSingleTop = true
                popUpTo("home") {
                    saveState = true
                    inclusive = false
                }
            } }
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuario"
            )
        }
    }
}