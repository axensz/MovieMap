package com.upbapps.moviemap.presentation.models

import com.google.gson.annotations.SerializedName

data class Serie(
    val id: Int = 0,
    val name: String = "",
    val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("genre_ids") val listGenress: List<Int> = emptyList(),
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("first_air_date") val first_air_date: String = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("overview") val overview: String = "",
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("platform") val platform: String? = null
)
