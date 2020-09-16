package com.swvltask.decadeofmovies.features.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swvltask.decadeofmovies.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesListFragment : Fragment() {


    val viewModel: MoviesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }


}