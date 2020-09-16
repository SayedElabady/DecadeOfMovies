package com.swvltask.decadeofmovies.shared.store.repo

import com.swvltask.decadeofmovies.shared.store.model.Movie

interface IMovieRepository{

    fun getAllMovies(): List<Movie>

}