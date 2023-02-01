package com.example.moovielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import com.example.moovielist.databinding.GridListBinding
import com.example.moovielist.databinding.HeaderListBinding
import com.example.moovielist.databinding.ListItemBinding
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.utils.Commons

sealed class Adapters :
    ListAdapter<RecyclerViewItem,RecyclerViewHolder>(ListMovieDiffUtil()) {

    class MovieAdapter(private val isLinear: Boolean, private val click: (View) -> Unit) :
        Adapters() {

        private val asyncListDiffer: AsyncListDiffer<RecyclerViewItem> = AsyncListDiffer(this, ListMovieDiffUtil())

        fun setMovieList(lives: List<RecyclerViewItem>) {
           asyncListDiffer.submitList(lives)
        }

        override fun getItemViewType(position: Int): Int {
            return when (asyncListDiffer.currentList[position]) {
                is RecyclerViewItem.Header -> Commons.HEADER_TYPE
                is RecyclerViewItem.MovieData -> Commons.LINEAR_TYPE
                else -> throw IllegalArgumentException("Invalid View type")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            return when (viewType) {
                Commons.HEADER_TYPE -> RecyclerViewHolder.HeaderViewHolder(
                    HeaderListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                Commons.LINEAR_TYPE -> {
                    if (isLinear) {
                        RecyclerViewHolder.LinearListViewHolder(
                            ListItemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        )
                    } else {
                        RecyclerViewHolder.GridListViewHolder(
                            GridListBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        )
                    }
                }
                else -> throw IllegalArgumentException("Invalid ViewHolder")
            }
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            when (holder) {
                is RecyclerViewHolder.HeaderViewHolder -> {
                    holder.bind(asyncListDiffer.currentList[position] as RecyclerViewItem.Header)
                    holder.iconImgClick.setOnClickListener(click)
                }
                is RecyclerViewHolder.LinearListViewHolder -> holder.bind(asyncListDiffer.currentList[position] as RecyclerViewItem.MovieData)
                is RecyclerViewHolder.GridListViewHolder -> holder.bind(asyncListDiffer.currentList[position] as RecyclerViewItem.MovieData)
            }

        }
        override fun getItemCount(): Int = asyncListDiffer.currentList.size
    }

}

