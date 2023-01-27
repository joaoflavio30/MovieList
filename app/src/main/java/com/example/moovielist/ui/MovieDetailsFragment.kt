package com.example.moovielist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moovielist.R
import com.example.moovielist.databinding.FragmentMovieDetailsBinding
import com.example.moovielist.repositories.MoviesDetailsRepository
import com.example.moovielist.rest.MovieService
import com.example.moovielist.utils.limitedDescription
import com.example.moovielist.viewmodel.MovieDetailsViewModel
import com.example.moovielist.viewmodel.MovieDetailsViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieId : String
    private val retrofitService = MovieService.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)

        movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieId.toString()
        viewModel =
            ViewModelProvider(this, MovieDetailsViewModelFactory(MoviesDetailsRepository(retrofitService,movieId)))[MovieDetailsViewModel::class.java]

        retrieveArgs()
        binding.backBtn.setOnClickListener {
            actionBack()
        }

         viewModel.getMoviesDetails()

      bindItems()
    }


    private fun actionBack() {
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToListFragment()
        findNavController().navigate(action)
    }

    private fun retrieveArgs() {

        arguments?.let {
            binding.apply {

                movieImg.load(MovieDetailsFragmentArgs.fromBundle(it).movieImage)
                movieTitle.text = MovieDetailsFragmentArgs.fromBundle(it).movieTitle
                note.text = MovieDetailsFragmentArgs.fromBundle(it).movieNote


            }

        }

    }

    private fun bindItems(){
        viewModel.overview.observe(viewLifecycleOwner){

            /* Passando para a funcao de construcao de dialogo o
              overview para definir limite de texto*/
            binding.descriptionContent.text = it.limitedDescription(250)
            if (it.isNullOrEmpty()) binding.descriptionContent.setText(R.string.no_description)
            if(it.length > 250) showDescriptionDialog(it)

        }
        viewModel.voteCount.observe((viewLifecycleOwner)){
            binding.voteCount.text = it.toString()
        }

        viewModel.genre.observe(viewLifecycleOwner){
            binding.genre.text = it
        }

    }

    // Criar dialogos para descrição
    private fun showDescriptionDialog(description: String) {
        binding.descriptionContent.setOnClickListener {
            createDescriptionDialog(description)
        }
    }

    private fun createDescriptionDialog(description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(binding.movieDescription.text)
            .setMessage(description)
            .setNegativeButton(
                R.string.closedialog
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


}