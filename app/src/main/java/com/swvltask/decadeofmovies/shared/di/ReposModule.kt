package com.swvltask.decadeofmovies.shared.di


import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import com.swvltask.decadeofmovies.shared.store.repo.MovieRepository
import com.swvltask.decadeofmovies.shared.store.sources.local.IMoviesProvider
import com.swvltask.decadeofmovies.shared.store.sources.local.MoviesProvider
import org.koin.dsl.module

val reposModule = module {

    single<IMoviesProvider> { MoviesProvider(get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }

}
