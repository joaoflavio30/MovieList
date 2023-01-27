package com.example.moovielist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moovielist.datasource.MovieData
import com.example.moovielist.datasource.MovieResponse
import com.example.moovielist.repositories.MoviesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieData>>()
    val movies: LiveData<List<MovieData>> = _movies

    private var _isLinearLayout = MutableLiveData(true)
    val isLinearLayout get() = _isLinearLayout


    fun switchBooleanLayout(value : Boolean) {
        _isLinearLayout.value = value

    }

    fun getMovies() {
        val responseMovie = repository.getMovies()
        responseMovie.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                val result = response.body()?.result
                _movies.value = result!!

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("failed", "" + t.message)
            }
        })
    }
}
