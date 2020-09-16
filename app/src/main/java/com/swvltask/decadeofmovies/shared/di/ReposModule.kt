package com.swvltask.decadeofmovies.shared.di


import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import com.swvltask.decadeofmovies.shared.store.repo.MovieRepository
import org.koin.dsl.module

val reposModule = module {


    single<IMovieRepository> { MovieRepository() }

}
