package com.example.moovielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moovielist.R
import com.example.moovielist.datasource.MovieData

class MovieAdapter(private val dataSet: List<MovieData>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object{
            const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        }

        fun bindData(movie: MovieData) {

            val movieImage = itemView.findViewById<ImageView>(R.id.movie_img)
            val movieName = itemView.findViewById<TextView>(R.id.movie_name)
            val movieDate = itemView.findViewById<TextView>(R.id.release_date)
            val movieVoteAverage = itemView.findViewById<TextView>(R.id.vote_average)

           movieImage.load(IMAGE_URL+movie.post)
            movieName.text = movie.originalTitle
            movieDate.text = movie.releaseDate
            movieVoteAverage.text = movie.voteAverage
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataSet[position])


    }

    override fun getItemCount(): Int = dataSet.size

}