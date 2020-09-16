package com.swvltask.decadeofmovies.shared.store.repo

import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.sources.api.IFlickrService
import com.swvltask.decadeofmovies.shared.store.sources.local.IMoviesProvider

class MovieRepository(
    private val moviesProvider: IMoviesProvider,
    private val flickerService: IFlickrService
) : IMovieRepository {
    override fun getAllMovies(): List<Movie> {
        return moviesProvider.getAllMovies()
    }

}