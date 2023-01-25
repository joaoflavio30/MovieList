package com.example.moovielist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moovielist.datasource.MovieData
import com.example.moovielist.datasource.MovieResponse
import com.example.moovielist.network.MoviesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    val listMovie = MutableLiveData<String>()
    val movies = MutableLiveData<List<MovieData>?>()

    init {
        getMovies()
        Log.v("teste","teste")
    }

     fun getMovies(){


                val responseMovie = MoviesApi.retrofitService.getMovies()
                responseMovie.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if(response.isSuccessful){
                            Log.d("movies" , ""+response.body())
                            val result = response.body()?.result
                           movies.value = result
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("failed",""+t.message)
                    }

                })





     }
    }

