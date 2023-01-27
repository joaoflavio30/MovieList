package com.example.moovielist.rest

import com.example.moovielist.datasource.MovieDetails
import com.example.moovielist.datasource.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("now_playing?api_key=b0b3c17892a58c619ef49d6e3010d379&language=pt-BR&page=1")
    fun getMovies(): Call<MovieResponse>

    @GET("{movie_id}?api_key=b0b3c17892a58c619ef49d6e3010d379&language=pt-BR")
    fun getDetails(@Path("movie_id") movieId: String): Call<MovieDetails>

    companion object {
        const val BASE_URL =
            "https://api.themoviedb.org/3/movie/"

        private val retrofitService: MovieService by lazy {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            retrofit.create(MovieService::class.java)
        }

        fun getInstance(): MovieService {
            return retrofitService
        }
    }
}