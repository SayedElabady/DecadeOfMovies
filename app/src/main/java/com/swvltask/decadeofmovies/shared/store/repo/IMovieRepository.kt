package com.swvltask.decadeofmovies.shared.store.repo

import com.swvltask.decadeofmovies.shared.store.model.Movie
import io.reactivex.Single

interface IMovieRepository{

    fun getAllMovies(): List<Movie>

    fun getMoviePhotosUrls(movieTitle :String): Single<List<String>>

}