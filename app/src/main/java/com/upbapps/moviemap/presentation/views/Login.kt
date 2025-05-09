package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upbapps.moviemap.R
import com.upbapps.moviemap.data.AuthManager

@Composable
fun Login(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.width(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "MovieApp",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Tu próxima maratón empieza aquí", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text(text = "Correo") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text(text = "Contraseña") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        TextButton(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(contentColor = Color.DarkGray)
        ) {
            Text(text = "¿Olvidaste tu contraseña?")
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = {
                isLoading = true
                errorMessage = null
                AuthManager.signIn(
                    email = correo,
                    password = contraseña,
                    onSuccess = { user ->
                        isLoading = false
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onError = { error ->
                        isLoading = false
                        errorMessage = error
                    }
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text(text = "Iniciar Sesión")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = { navController.navigate("register") },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crea tu cuenta")
        }
    }
}