package com.swvltask.decadeofmovies.shared.di

import com.swvltask.decadeofmovies.shared.store.model.FlickerPicture
import com.swvltask.decadeofmovies.shared.store.model.IMapper
import com.swvltask.decadeofmovies.shared.store.model.MoviePhotoMapper
import org.koin.dsl.module

val mappersModule = module {
    single<IMapper<FlickerPicture, String>> { MoviePhotoMapper() }
}