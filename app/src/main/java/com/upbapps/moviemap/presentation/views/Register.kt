package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Register (navController: NavHostController){
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var RepiteContraseña by remember {mutableStateOf("")}
    Row (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ){
        //Spacer(modifier = Modifier.height(80.dp))
        IconButton(
            onClick = {navController.popBackStack()}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }
    }
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MovieApp",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Logo")
        Text(text = "Tu próxima maratón empieza aquí", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(100.dp))
        OutlinedTextField(
            value = correo,
            onValueChange = {correo = it},
            label = {Text(text = "Correo")},
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = contraseña,
            onValueChange = {contraseña = it},
            label = {Text(text = "Contraseña")},
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = RepiteContraseña,
            onValueChange = {RepiteContraseña = it},
            label = {Text(text = "Repetir Contraseña")},
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {navController.navigate("home")},
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "Crear cuenta")
        }
    }
}