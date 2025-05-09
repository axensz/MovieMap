package com.upbapps.moviemap.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.models.Serie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
@Composable
fun SerieItem(navController: NavController, serie: Serie) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        modifier = Modifier.clickable(onClick = {
            val serieJSON = Uri.encode(Gson().toJson(serie))
            navController.navigate("details_serie/$serieJSON")
        })
    ) {
        Column (
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AsyncImage(
                model = IMAGE_BASE_URL + serie.posterPath,
                contentDescription = serie.name,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(serie.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}
