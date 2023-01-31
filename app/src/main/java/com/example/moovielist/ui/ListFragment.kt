package com.example.moovielist.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovielist.R
import com.example.moovielist.adapter.Adapters
import com.example.moovielist.adapter.RecyclerViewHolder
import com.example.moovielist.databinding.FragmentListBinding
import com.example.moovielist.databinding.HeaderListBinding
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.repositories.MoviesRepository
import com.example.moovielist.rest.MovieService
import com.example.moovielist.utils.Commons
import com.example.moovielist.viewmodel.MovieViewModel
import com.example.moovielist.viewmodel.MovieViewModelFactory


class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: com.example.moovielist.databinding.FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieViewModel

    private var linearAdapter = Adapters.MovieAdapter() {
        clickIcon()
    }
    private val retrofitService = MovieService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, MovieViewModelFactory(MoviesRepository(retrofitService))).get(
                MovieViewModel::class.java
            )
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = linearAdapter
        setDataRecyclerView(R.drawable.ic_grid_view)
        linearAdapter.setOnImageClickListener(object : Adapters.MovieAdapter.OnImageClickListener {
            override fun onImageClick(position: Int) {
                clickIcon()
            }

        })

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setDataRecyclerView(iconImage: Int) {
        linearAdapter.setMovieList(
            listOf(
                RecyclerViewItem.Header(
                    iconImage,
                    getString(R.string.header_title),
                    getString(R.string.header_year)
                )
            )
        )

        viewModel.movies.observe(viewLifecycleOwner) {
            val list = mutableListOf<RecyclerViewItem>()
           list.add(
               RecyclerViewItem.Header(
                   iconImage,
                   getString(R.string.header_title),
                   getString(R.string.header_year)
               )
           )
            it.forEach { item -> list.add(item) }

            linearAdapter.setMovieList(list)

        }

    }

    private fun clickIcon() {
        Commons.IS_LINEAR = !Commons.IS_LINEAR

        if (Commons.IS_LINEAR) {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            linearAdapter = Adapters.MovieAdapter { clickIcon() }
            binding.recyclerView.adapter = linearAdapter
            setDataRecyclerView(R.drawable.ic_grid_view)
        } else {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (position) {
                        0 -> 2
                        else -> 1
                    }
                }
            }
            binding.recyclerView.layoutManager = layoutManager
            linearAdapter = Adapters.MovieAdapter { clickIcon() }
            binding.recyclerView.adapter = linearAdapter
            setDataRecyclerView(R.drawable.ic_list_view)

        }


    }

}


