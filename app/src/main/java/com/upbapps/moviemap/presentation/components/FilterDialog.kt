package com.upbapps.moviemap.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onApplyFilter: (year: String, rating: String, genre: String, ageRating: String) -> Unit
) {
    var year by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var ageRating by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onApplyFilter(year, rating, genre, ageRating)
                    onDismiss()
                }) {
                    Text("Aplicar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            },
            title = { Text("Filtrar contenido") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = year,
                        onValueChange = { year = it },
                        label = { Text("Año de estreno") }
                    )
                    OutlinedTextField(
                        value = rating,
                        onValueChange = { rating = it },
                        label = { Text("Calificación mínima (0–10)") }
                    )
                    OutlinedTextField(
                        value = genre,
                        onValueChange = { genre = it },
                        label = { Text("Género") }
                    )
                    OutlinedTextField(
                        value = ageRating,
                        onValueChange = { ageRating = it },
                        label = { Text("Clasificación por edad (true/false)") }
                    )
                }
            }
        )
    }
}


