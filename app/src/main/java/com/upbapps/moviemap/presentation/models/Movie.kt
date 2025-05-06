package com.upbapps.moviemap.presentation.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?
)
