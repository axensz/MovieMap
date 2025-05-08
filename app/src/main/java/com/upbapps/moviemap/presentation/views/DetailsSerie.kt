package com.upbapps.moviemap.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.upbapps.moviemap.presentation.models.Serie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun DetailsSerie(navController: NavController, serie: Serie){
    val genres_names = serie.list_genres.mapNotNull { genresSeriesMap[it] }
    Column {
        Header(navController)
        AsyncImage(
            model = IMAGE_BASE_URL + serie.backdropPath,
            contentDescription = serie.name,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column{
                Text(serie.name, style = MaterialTheme.typography.titleMedium)
                LazyRow {
                    items(genres_names) { genre ->
                        genreView(genre)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
                Text(serie.overview, modifier = Modifier.padding(top = 15.dp, start = 7.dp), style = MaterialTheme.typography.bodyMedium)
                Text(serie.first_air_date, modifier = Modifier.padding(top = 15.dp, start = 7.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun genreSerieView(genre: String){

    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

val genresSeriesMap = mapOf(
    10759 to "Acción y aventura",
    16 to "Animación",
    35 to "Comedia",
    80 to "Crimen",
    99 to "Documental",
    18 to "Drama",
    10751 to "Familia",
    10762 to "Niños",
    10763 to "Noticias",
    10764 to "Realidad",
    10765 to "Fantasía y Sci Fi",
    10770 to "Película TV",
    10766 to "Soap",
    10637 to "Hablar",
    10738 to "Guerra y política",
    37 to "Western"
)