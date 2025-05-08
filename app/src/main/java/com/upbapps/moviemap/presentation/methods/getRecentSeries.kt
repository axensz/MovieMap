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
import java.io.IOException

fun getRecentSeries(onResult: (List<Movie>) -> Unit){
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/tv/on_the_air?&language=es-ES&page=1")
        .addHeader("Authorization", "Bearer "+ tk)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException){
            Log.e("Error", e.message ?: "")
        }
        override fun onResponse(call: Call, response: Response){
            response.body?.string().let {json ->
                val gson = Gson()
                val movieResponse = gson.fromJson(json, MovieResponse::class.java)
                onResult(movieResponse.results ?: emptyList())
            }
        }
    })
}