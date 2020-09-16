package com.swvltask.decadeofmovies.shared.store.sources.local

import com.swvltask.decadeofmovies.shared.store.model.Movie

interface IMoviesProvider{

    fun getAllMovies(): List<Movie>
}