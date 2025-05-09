package com.upbapps.moviemap.presentation.methods

import android.util.Log
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.MovieResponse
import com.upbapps.moviemap.ui.theme.tk
import okhttp3.*
import java.io.IOException

fun getFilteredMovies(
    year: String?,
    rating: String?,
    genreId: String?,
    onResult: (List<Movie>) -> Unit
) {
    val client = OkHttpClient()

    val urlBuilder = HttpUrl.Builder()
        .scheme("https")
        .host("api.themoviedb.org")
        .addPathSegments("3/discover/movie")
        .addQueryParameter("language", "es-ES")
        .addQueryParameter("include_adult", "false")
        .addQueryParameter("include_video", "false")
        .addQueryParameter("page", "1")
        .addQueryParameter("sort_by", "popularity.desc")

    year?.takeIf { it.isNotEmpty() }?.let {
        urlBuilder.addQueryParameter("primary_release_year", it)
    }

    rating?.takeIf { it.isNotEmpty() }?.toFloatOrNull()?.let {
        // TMDB solo acepta `vote_average.gte` (mayor o igual)
        urlBuilder.addQueryParameter("vote_average.gte", (it * 2).toInt().toString()) // escalar a 10
    }

    genreId?.takeIf { it.isNotEmpty() }?.let {
        urlBuilder.addQueryParameter("with_genres", it)
    }

    val request = Request.Builder()
        .url(urlBuilder.build())
        .addHeader("Authorization", "Bearer $tk")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("API_ERROR", e.message ?: "")
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.results ?: emptyList())
            }
        }
    })
}


