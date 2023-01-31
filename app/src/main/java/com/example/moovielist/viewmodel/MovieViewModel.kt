package com.example.moovielist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.repositories.MoviesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<RecyclerViewItem>>()
    val movies: LiveData<List<RecyclerViewItem>> = _movies

    private var _isLinearLayout = MutableLiveData(true)
    val isLinearLayout get() = _isLinearLayout


    fun switchBooleanLayout() {
        _isLinearLayout.value = !_isLinearLayout.value!!

    }

    fun getMovies() {
        val responseMovie = repository.getMovies()
        responseMovie.enqueue(object : Callback<RecyclerViewItem.MovieResponse> {
            override fun onResponse(
                call: Call<RecyclerViewItem.MovieResponse>,
                response: Response<RecyclerViewItem.MovieResponse>
            ) {
                val result = response.body()?.result
                _movies.value = result!!

            }

            override fun onFailure(call: Call<RecyclerViewItem.MovieResponse>, t: Throwable) {
                Log.e("failed", "" + t.message)
            }
        })
    }
}
