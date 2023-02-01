package com.example.moovielist.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.example.moovielist.databinding.GridListBinding
import com.example.moovielist.databinding.HeaderListBinding
import com.example.moovielist.databinding.ListItemBinding
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.ui.ListFragmentDirections
import com.example.moovielist.utils.Commons
import com.example.moovielist.utils.limitedDescription

sealed class RecyclerViewHolder(_binding: ViewBinding) : RecyclerView.ViewHolder(_binding.root) {

    class HeaderViewHolder(private val binding : HeaderListBinding) : RecyclerViewHolder(binding){

        fun bind(headerItems : RecyclerViewItem.Header){
            binding.titleHeader.text = headerItems.title
            binding.iconLayoutManager.setImageResource(headerItems.iconImage)
            binding.year.text = headerItems.year
        }
        var iconImgClick = binding.iconLayoutManager
    }

    class LinearListViewHolder(private val binding : ListItemBinding) : RecyclerViewHolder(binding){

        fun bind(movie : RecyclerViewItem.MovieData){
            binding.movieImg.load(Commons.IMAGE_URL + movie.post)
            binding.movieName.text = movie.originalTitle.limitedDescription(29)
            binding.releaseDate.text = movie.releaseDate
            binding.voteAverage.text = movie.voteAverage

            val action = ListFragmentDirections.actionListFragmentToMovieDetailsFragment(Commons.IMAGE_URL + movie.post,movie.voteAverage,movie.originalTitle,movie.movieId)
            binding.cardMovie.setOnClickListener {
                this.itemView.findNavController().navigate(action)
        }
        }
    }

    class GridListViewHolder( private val binding : GridListBinding) : RecyclerViewHolder(binding){

        fun bind(movie: RecyclerViewItem.MovieData){
            binding.movieImg.load(Commons.IMAGE_URL + movie.post)
            binding.movieName.text = movie.originalTitle
            val action = ListFragmentDirections.actionListFragmentToMovieDetailsFragment(Commons.IMAGE_URL + movie.post,movie.voteAverage,movie.originalTitle,movie.movieId)
            binding.cardMovie.setOnClickListener {
                this.itemView.findNavController().navigate(action)
            }
        }

    }

}