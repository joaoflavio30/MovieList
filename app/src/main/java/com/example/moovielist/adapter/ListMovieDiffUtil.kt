package com.example.moovielist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moovielist.datasource.RecyclerViewItem

class ListMovieDiffUtil : DiffUtil.ItemCallback<RecyclerViewItem>() {

    override fun areItemsTheSame(
        oldItem: RecyclerViewItem,
        newItem: RecyclerViewItem
    ): Boolean = when {
        oldItem is RecyclerViewItem.Header && newItem is RecyclerViewItem.Header -> oldItem.title == newItem.title
        oldItem is RecyclerViewItem.MovieData && newItem is RecyclerViewItem.MovieData -> oldItem.movieId == newItem.movieId
        else -> false
    }

    override fun areContentsTheSame(
        oldItem: RecyclerViewItem,
        newItem: RecyclerViewItem
    ): Boolean = when {
            oldItem is RecyclerViewItem.Header && newItem is RecyclerViewItem.Header -> oldItem == newItem
            oldItem is RecyclerViewItem.MovieData && newItem is RecyclerViewItem.MovieData -> oldItem == newItem
            else -> false
        }
    }
