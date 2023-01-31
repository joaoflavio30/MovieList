package com.example.moovielist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.repositories.MoviesDetailsRepository
import com.example.moovielist.rest.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel constructor(private val repository: MoviesDetailsRepository) : ViewModel() {

    private val _moviesDetails = MutableLiveData<List<RecyclerViewItem.MovieData>>()
    val moviesDetails : LiveData<List<RecyclerViewItem.MovieData>> = _moviesDetails

    private var _movieId = MutableLiveData<Int>()
    val movieId get() = _movieId


    private var _overview = MutableLiveData<String>()
    val overview get() = _overview

    private var _genre = MutableLiveData<String>()
    val genre get() = _genre

    private var _voteCount = MutableLiveData<Int>()
    val voteCount get() = _voteCount


    fun getMoviesDetails(){

        val responseDetails = repository.getDetails()
        responseDetails.enqueue(object : Callback<RecyclerViewItem.MovieDetails> {
            override fun onResponse(call: Call<RecyclerViewItem.MovieDetails>, response: Response<RecyclerViewItem.MovieDetails>) {
                    response.body()?.apply {
                        _overview.value = this.overview
                        _voteCount.value = this.voteCount

                        // Concatenando Strings de generos que na api é um array de objetos com o nome do genero
                        this.genres.forEach {
                           if(_genre.value.isNullOrEmpty()) _genre.value = it.genreName
                           else _genre.value += " - " + it.genreName
                        }
                    }
            }

            override fun onFailure(call: Call<RecyclerViewItem.MovieDetails>, t: Throwable) {
                Log.e("failed", "" + t.message)
            }
        })

    }

}