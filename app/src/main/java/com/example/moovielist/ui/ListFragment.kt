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
import com.example.moovielist.databinding.FragmentListBinding
import com.example.moovielist.datasource.RecyclerViewItem
import com.example.moovielist.repositories.MoviesRepository
import com.example.moovielist.rest.MovieService
import com.example.moovielist.viewmodel.MovieViewModel
import com.example.moovielist.viewmodel.MovieViewModelFactory

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieViewModel

    private lateinit var linearAdapter : Adapters.MovieAdapter
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
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel.isLinearLayout.observe(viewLifecycleOwner){

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearAdapter = Adapters.MovieAdapter(viewModel.isLinearLayout.value!!) {
            clickIcon()
        }
        binding.recyclerView.adapter = linearAdapter
       if(viewModel.isLinearLayout.value!!){
           binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
           setDataRecyclerView(R.drawable.ic_grid_view)
       }
       else {gridLayoutManager()
           setDataRecyclerView(R.drawable.ic_list_view)
       }

    }

    private fun setDataRecyclerView(iconImage: Int) {
        viewModel.movies.observe(viewLifecycleOwner) {
            val list = mutableListOf<RecyclerViewItem>()
           list.add(
               RecyclerViewItem.Header(
                   iconImage,
                   getString(R.string.header_title),
                   getString(R.string.header_year)))
            it.forEach { item -> list.add(item) }

            linearAdapter.setMovieList(list) }
    }

    private fun clickIcon() {
        viewModel.switchBooleanLayout()
        viewModel.isLinearLayout.observe(viewLifecycleOwner){
            if (it) {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                linearAdapter = Adapters.MovieAdapter(it) { clickIcon() }
                binding.recyclerView.adapter = linearAdapter
                setDataRecyclerView(R.drawable.ic_grid_view)
            } else {
                 gridLayoutManager()
                linearAdapter = Adapters.MovieAdapter(it) { clickIcon() }
                binding.recyclerView.adapter = linearAdapter
                setDataRecyclerView(R.drawable.ic_list_view)
            }
        }
    }

    private fun gridLayoutManager(){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 2
                    else -> 1 } } }
        binding.recyclerView.layoutManager = layoutManager
    }
}


