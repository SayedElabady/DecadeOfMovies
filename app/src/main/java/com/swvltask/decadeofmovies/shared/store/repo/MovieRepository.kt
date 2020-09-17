package com.swvltask.decadeofmovies.shared.store.repo

import com.swvltask.decadeofmovies.shared.store.model.FlickerPicture
import com.swvltask.decadeofmovies.shared.store.model.IMapper
import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.sources.api.IFlickrService
import com.swvltask.decadeofmovies.shared.store.sources.local.IMoviesProvider
import io.reactivex.Single

class MovieRepository(
    private val moviesProvider: IMoviesProvider,
    private val flickerService: IFlickrService,
    private val photosMapper: IMapper<FlickerPicture, String>
) : IMovieRepository {
    override fun getAllMovies(): List<Movie> {
        return moviesProvider.getAllMovies()
    }

    override fun getMoviePhotosUrls(movieTitle: String): Single<List<String>> {
        return flickerService.getMoviePhotos(movieTitle).map {
            photosMapper.mapList(it.photos.photo)
        }
    }

}