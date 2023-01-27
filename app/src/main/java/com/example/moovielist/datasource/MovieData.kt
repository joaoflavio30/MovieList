package com.example.moovielist.datasource

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieData(
    @Json(name = "title") val originalTitle: String,
    @Json(name = "vote_average") val voteAverage : String,
    @Json(name = "release_date") val releaseDate : String,
    @Json(name = "poster_path") val post : String,
    @Json(name = "overview") var description : String,
    @Json(name = "id") val movieId : Int
)

data class MovieResponse(@Json(name = "results")
val result : List<MovieData>)

data class MovieDetails(@Json(name = "overview") val overview : String,
@Json(name = "vote_count")val voteCount : Int,
@Json(name = "genres") val genres : List<MovieGenre>)


//propridade dentro de genres na response da api do detalhes dos filmes
data class MovieGenre(@Json(name = "name") val genreName : String)