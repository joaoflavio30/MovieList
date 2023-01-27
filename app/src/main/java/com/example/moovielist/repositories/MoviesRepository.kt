package com.example.moovielist.repositories

import com.example.moovielist.rest.MovieService

class MoviesRepository constructor(
    private val movieService: MovieService,
) {

    fun getMovies() = movieService.getMovies()


}