package com.example.moovielist.datasource

import com.squareup.moshi.Json

sealed class RecyclerViewItem {
    data class MovieData(
        @Json(name = "title") val originalTitle: String,
        @Json(name = "vote_average") val voteAverage : String,
        @Json(name = "release_date") val releaseDate : String,
        @Json(name = "poster_path") val post : String,
        @Json(name = "overview") var description : String,
        @Json(name = "id") val movieId : Int
    ) : RecyclerViewItem()

    data class MovieResponse(@Json(name = "results")
                             val result : List<MovieData>) : RecyclerViewItem()

    data class MovieDetails(@Json(name = "overview") val overview : String,
                            @Json(name = "vote_count")val voteCount : Int,
                            @Json(name = "genres") val genres : List<MovieGenre>) : RecyclerViewItem()


    //propridade dentro de genres na response da api do detalhes dos filmes
    data class MovieGenre(@Json(name = "name") val genreName : String) : RecyclerViewItem()

    data class Header(val iconImage: Int, val title: String, val year: String) : RecyclerViewItem()

}

