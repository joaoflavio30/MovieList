package com.example.moovielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovielist.adapter.MovieAdapter
import com.example.moovielist.databinding.FragmentListBinding
import com.example.moovielist.viewmodel.MovieViewModel


class ListFragment : Fragment() {

    private var binding : FragmentListBinding? = null
    private val viewModel : MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBiding = FragmentListBinding.inflate(inflater,container,false)
        binding = fragmentBiding
        return  fragmentBiding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies()

        viewModel.movies.observe(requireActivity()){
            val adapter = viewModel.movies.value?.let { MovieAdapter(it) }
            val recyclerView = binding?.recyclerView
            recyclerView?.layoutManager = LinearLayoutManager(requireContext())
            recyclerView?.setHasFixedSize(true)

            recyclerView?.adapter = adapter
        }






    }
}