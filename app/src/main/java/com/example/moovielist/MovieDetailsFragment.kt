package com.example.moovielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import coil.load
import com.example.moovielist.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let {
            binding?.movieImg?.load(MovieDetailsFragmentArgs.fromBundle(it).movieImage)
            binding?.movieTitle?.text = MovieDetailsFragmentArgs.fromBundle(it).movieTitle
            binding?.note?.text = MovieDetailsFragmentArgs.fromBundle(it).movieNote
            binding?.descriptionContent?.text =
                MovieDetailsFragmentArgs.fromBundle(it).movieDescription
        }

    }


}