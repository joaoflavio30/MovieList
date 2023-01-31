package com.example.moovielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moovielist.R
import com.example.moovielist.databinding.GridListBinding
import com.example.moovielist.databinding.HeaderListBinding
import com.example.moovielist.databinding.ListItemBinding
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.utils.Commons

sealed class Adapters :
    RecyclerView.Adapter<RecyclerViewHolder>() {


    class MovieAdapter(private val click : (View) -> Unit) : Adapters() {

        var dataSet = listOf<RecyclerViewItem>()

        fun setMovieList(lives: List<RecyclerViewItem>) {
            this.dataSet = lives
            notifyDataSetChanged()
        }

        interface OnImageClickListener {
            fun onImageClick(position: Int)
        }

        private var imageListener: OnImageClickListener? = null

        fun setOnImageClickListener(listener: OnImageClickListener) {
            this.imageListener = listener
        }


        override fun getItemViewType(position: Int): Int {
            return when (dataSet[position]) {
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
                    if (Commons.IS_LINEAR) {
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
                is RecyclerViewHolder.HeaderViewHolder ->{
                    holder.bind(dataSet[position] as RecyclerViewItem.Header)
                    holder.iconImgClick.setOnClickListener(click)
                }
                is RecyclerViewHolder.LinearListViewHolder -> holder.bind(dataSet[position] as RecyclerViewItem.MovieData)
                is RecyclerViewHolder.GridListViewHolder -> holder.bind(dataSet[position] as RecyclerViewItem.MovieData)
            }

        }

        override fun getItemCount(): Int = dataSet.size


    }

    class GridMovieAdapter : Adapters() {

        var dataSet = listOf<RecyclerViewItem>()

        fun setMovieList(lives: List<RecyclerViewItem>) {
            this.dataSet = lives
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int): Int {
            return when (dataSet[position]) {
                is RecyclerViewItem.Header -> Commons.HEADER_TYPE
                else -> Commons.GRID_TYPE
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            return when (viewType) {
                0 -> RecyclerViewHolder.HeaderViewHolder(
                    HeaderListBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
                2 -> RecyclerViewHolder.GridListViewHolder(
                    GridListBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
                else -> throw IllegalArgumentException("Invalid ViewHolder")
            }
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            when (holder) {
                is RecyclerViewHolder.HeaderViewHolder -> holder.bind(dataSet[position] as RecyclerViewItem.Header)
                is RecyclerViewHolder.GridListViewHolder -> holder.bind((dataSet[position] as RecyclerViewItem.MovieData))
                else -> throw IllegalArgumentException("Invalid BindViewHolder")
            }

        }

        override fun getItemCount(): Int = dataSet.size


    }

}

