package com.swvltask.decadeofmovies.shared.di

import com.swvltask.decadeofmovies.features.moviesList.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {

    viewModel {
        MoviesListViewModel(get())
    }
}