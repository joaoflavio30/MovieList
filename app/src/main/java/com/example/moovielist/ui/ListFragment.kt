package com.example.moovielist.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovielist.R
import com.example.moovielist.adapter.GridMovieAdapter
import com.example.moovielist.adapter.MovieAdapter
import com.example.moovielist.databinding.FragmentListBinding
import com.example.moovielist.repositories.MoviesRepository
import com.example.moovielist.rest.MovieService
import com.example.moovielist.viewmodel.MovieViewModel
import com.example.moovielist.viewmodel.MovieViewModelFactory


class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieViewModel

    private var linearAdapter = MovieAdapter()
    private var gridAdapter = GridMovieAdapter()

    private val retrofitService = MovieService.getInstance()
    private var isLinearLayout = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        viewModel =
            ViewModelProvider(this, MovieViewModelFactory(MoviesRepository(retrofitService))).get(
                MovieViewModel::class.java
            )

        binding.iconLayoutManager.setOnClickListener {
           switchLayout()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.movies.observe(this) {
            linearAdapter.setMovieList(it)
            gridAdapter.setMovieList(it)
        }

        viewModel.isLinearLayout.observe(viewLifecycleOwner){
            isLinearLayout = it
        }
        initRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovies()

    }
    private fun initRecyclerView(){
        if(!isLinearLayout){binding.iconLayoutManager.setImageResource(R.drawable.ic_list_view)
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            binding.recyclerView.adapter = gridAdapter}else{
            binding.iconLayoutManager.setImageResource(R.drawable.ic_grid_view)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = linearAdapter
        }

    }


    private fun switchLayout(){
        isLinearLayout = !isLinearLayout
        if(!isLinearLayout){binding.iconLayoutManager.setImageResource(R.drawable.ic_list_view)
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            binding.recyclerView.adapter = gridAdapter}else{
            binding.iconLayoutManager.setImageResource(R.drawable.ic_grid_view)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = linearAdapter
        }
        viewModel.switchBooleanLayout(isLinearLayout)
    }

}

