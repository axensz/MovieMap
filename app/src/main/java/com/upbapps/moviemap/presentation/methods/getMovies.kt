package com.upbapps.moviemap.presentation.methods

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.MovieResponse
import com.upbapps.moviemap.presentation.models.Serie
import com.upbapps.moviemap.ui.theme.tk
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONException
import java.io.IOException
import org.json.JSONObject

fun getMovies(onResult: (List<Movie>) -> Unit) {
    val client = OkHttpClient()
    val urlBuilder = "https://api.themoviedb.org/3/discover/movie".toHttpUrlOrNull()?.newBuilder()
        ?.addQueryParameter("language", "es-CO") // Changed to es-CO
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
            onResult(emptyList()) // Report error by returning empty list
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.results)
            } ?: run {
                Log.e("Error", "Empty response body")
                onResult(emptyList()) // Report error by returning empty list
            }
        }
    })
}

fun getFilteredMovies(
    year: String = "",
    rating: String = "",
    genre: String = "",
    mediaType: String = "movie",
    onMovieResult: (List<Movie>) -> Unit = {},
    onSerieResult: (List<Serie>) -> Unit = {}
) {
    val client = OkHttpClient()
    val baseUrlBuilder = HttpUrl.Builder()
        .scheme("https")
        .host("api.themoviedb.org")
        .addPathSegment("3")
        .addQueryParameter("api_key", tk) // Using tk constant as API Key
        .addQueryParameter("language", "es-CO")

    if (mediaType == "movie") {
        baseUrlBuilder.addPathSegment("discover")
        baseUrlBuilder.addPathSegment("movie")
    } else if (mediaType == "tv") {
        baseUrlBuilder.addPathSegment("discover")
        baseUrlBuilder.addPathSegment("tv")
    }

    if (year.isNotEmpty()) {
        baseUrlBuilder.addQueryParameter("primary_release_year", year)
    }
    if (rating.isNotEmpty()) {
        val vote = rating.toFloatOrNull()
        if (vote != null) {
            baseUrlBuilder.addQueryParameter("vote_average", vote.toString()) // Correction: Use rating directly
        }
    }
    if (genre.isNotEmpty()) {
        baseUrlBuilder.addQueryParameter("with_genres", genre)
    }

    val url = baseUrlBuilder.build()
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("getFilteredMovies", "API call error: ${e.message}")
            if (mediaType == "movie") {
                onMovieResult(emptyList())
            } else if (mediaType == "tv") {
                onSerieResult(emptyList())
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body?.string()
            if (response.isSuccessful && responseBody != null) {
                try {
                    val jsonObject = JSONObject(responseBody)
                    val resultsArray = jsonObject.getJSONArray("results")
                    val gson = Gson()
                    if (mediaType == "movie") {
                        val movies = gson.fromJson<List<Movie>>(resultsArray.toString(), object : TypeToken<List<Movie>>() {}.type)
                        onMovieResult(movies)
                    } else if (mediaType == "tv") {
                        val series = gson.fromJson<List<Serie>>(resultsArray.toString(), object : TypeToken<List<Serie>>() {}.type)
                        onSerieResult(series)
                    }
                } catch (e: JSONException) {
                    Log.e("getFilteredMovies", "JSON parsing error: ${e.message}")
                    if (mediaType == "movie") {
                        onMovieResult(emptyList())
                    } else if (mediaType == "tv") {
                        onSerieResult(emptyList())
                    }
                }
            } else {
                Log.e("getFilteredMovies", "Unsuccessful response: ${response.code} - $responseBody")
                if (mediaType == "movie") {
                    onMovieResult(emptyList())
                } else if (mediaType == "tv") {
                    onSerieResult(emptyList())
                }
            }
        }
    })
}