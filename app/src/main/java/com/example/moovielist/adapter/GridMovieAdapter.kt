package com.example.moovielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moovielist.ui.ListFragmentDirections
import com.example.moovielist.R
import com.example.moovielist.adapter.MovieAdapter.ViewHolder.Companion.IMAGE_URL
import com.example.moovielist.datasource.MovieData

class GridMovieAdapter :
    RecyclerView.Adapter<GridMovieAdapter.ViewHolder>() {


private var dataSet = mutableListOf<MovieData>()

    fun setMovieList(lives: List<MovieData>) {
        this.dataSet = lives.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        }

        fun bindData(movie: MovieData) {

            val movieImage = itemView.findViewById<ImageView>(R.id.movie_img)
            val movieName = itemView.findViewById<TextView>(R.id.movie_name)

            movieImage.load(IMAGE_URL + movie.post)
            movieName.text = movie.originalTitle
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = LayoutInflater.from(parent.context).inflate(R.layout.grid_list, parent, false)

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataSet[position])

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToMovieDetailsFragment(
                IMAGE_URL + dataSet[position].post,
                dataSet[position].originalTitle,
                dataSet[position].voteAverage,
                dataSet[position].movieId
            )
            val navController = holder.itemView.findNavController()
            navController.navigate(action)

        }
    }

    override fun getItemCount(): Int = dataSet.size


}