package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Header(navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Text(text = "MovieMap",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = {navController.navigate("user")}
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuario"
            )
        }
    }
}