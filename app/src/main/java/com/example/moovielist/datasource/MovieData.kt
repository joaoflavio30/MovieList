package com.example.moovielist.datasource

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieData(
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "vote_average") val voteAverage : String,
    @Json(name = "release_date") val releaseDate : String,
    @Json(name = "poster_path") val post : String

)

data class MovieResponse(@Json(name = "results")
val result : List<MovieData>)