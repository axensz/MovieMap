package com.upbapps.moviemap.presentation.methods

import android.util.Log
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.MovieResponse
import com.upbapps.moviemap.ui.theme.tk
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

fun getMovies(onResult: (List<Movie>) -> Unit) {
    val client = OkHttpClient()
    val urlBuilder = "https://api.themoviedb.org/3/discover/movie".toHttpUrlOrNull()?.newBuilder()
        ?.addQueryParameter("language", "es-ES")
        ?.addQueryParameter("include_adult", "false")
        ?.addQueryParameter("include_video", "false")
        ?.addQueryParameter("page", "1")
        ?.addQueryParameter("sort_by", "popularity.desc")

    val request = Request.Builder()
        .url(urlBuilder?.build().toString())
        .addHeader("Authorization", "Bearer $tk")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Error", e.message ?: "")
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.results) // Ajustado para no usar el operador Elvis innecesariamente
            }
        }
    })
}

fun getFilteredMovies(
    year: String = "",
    rating: String = "",
    genre: String = "",
    onResult: (List<Movie>) -> Unit
) {
    val client = OkHttpClient()
    val urlBuilder = "https://api.themoviedb.org/3/discover/movie".toHttpUrlOrNull()?.newBuilder()
        ?.addQueryParameter("language", "es-ES")
        ?.addQueryParameter("include_adult", "false")
        ?.addQueryParameter("include_video", "false")
        ?.addQueryParameter("page", "1")
        ?.addQueryParameter("sort_by", "popularity.desc")

    if (year.isNotEmpty()) {
        urlBuilder?.addQueryParameter("primary_release_year", year)
    }
    if (rating.isNotEmpty()) {
        val vote = rating.toFloatOrNull()
        if (vote != null) {
            urlBuilder?.addQueryParameter("vote_average.gte", (vote * 2).toString())
        }
    }
    if (genre.isNotEmpty()) {
        urlBuilder?.addQueryParameter("with_genres", genre)
    }

    val request = Request.Builder()
        .url(urlBuilder?.build().toString())
        .addHeader("Authorization", "Bearer $tk")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Filter Error", e.message ?: "")
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.results)             }
        }
    })
}