package com.swvltask.decadeofmovies.shared.di

import com.swvltask.decadeofmovies.features.moviedetails.MovieDetailsViewModel
import com.swvltask.decadeofmovies.features.moviesList.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelsModule = module {

    viewModel {
        MoviesListViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }
}