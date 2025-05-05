package com.upbapps.moviemap.presentation.methods

import android.util.Log
import com.google.gson.Gson
import com.upbapps.moviemap.presentation.models.Movie
import com.upbapps.moviemap.presentation.models.MovieResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

fun getMovies(onResult: (List<Movie>) -> Unit){
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MmQzZTlmZTM2MGYxZDdjZmQxNDMwNjljYjgyZmI5MiIsIm5iZiI6MTc0NjQwODEyOC45NDQsInN1YiI6IjY4MTgxMmMwYWVmMmU1M2M2ZWQ0Y2I5ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.doIdIc-Dk95w2SLqLsiR-FSZRrkzdbBg9PajkEN-fVM")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException){
            Log.e("Error", e.message ?: "")
        }
        override fun onResponse(call: Call, response: Response){
            response.body?.string().let {json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.movies ?: emptyList())
            }
        }
    })

}