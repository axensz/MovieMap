package com.upbapps.moviemap.presentation.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val listGenress: List<Int>,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("adult") val adult: Boolean?
)
