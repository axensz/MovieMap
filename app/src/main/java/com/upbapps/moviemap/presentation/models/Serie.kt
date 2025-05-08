package com.upbapps.moviemap.presentation.models

import com.google.gson.annotations.SerializedName

data class Serie(
    val id: Int,
    val name: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val list_genres: List<Int>,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("overview") val overview: String,
)
