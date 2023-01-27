package com.example.moovielist.repositories

import com.example.moovielist.rest.MovieService

class MoviesDetailsRepository constructor(private val movieService: MovieService ,private val movieId : String) {

    fun getDetails() = movieService.getDetails(movieId)
}