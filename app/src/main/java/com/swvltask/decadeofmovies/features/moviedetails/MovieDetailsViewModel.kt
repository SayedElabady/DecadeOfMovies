package com.swvltask.decadeofmovies.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val moviesRepository: IMovieRepository) : ViewModel() {

    var disposable: Disposable? = null

    private val _state = MutableLiveData<MovieDetailsState>()

    val state: LiveData<MovieDetailsState> = _state

    fun fetchPhotos(movieTitle: String) {
        _state.value = MovieDetailsState.LoadingPhotos
        disposable = moviesRepository.getMoviePhotosUrls(movieTitle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = MovieDetailsState.OnPhotosReady(it)
            }) {
                _state.value = MovieDetailsState.ErrorLoadingPhotos(it)
            }
    }

    fun processEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.InitialEvent -> fetchPhotos(event.movie.title)
        }
    }

}

sealed class MovieDetailsState {
    data class OnPhotosReady(val photosUrls: List<String>) : MovieDetailsState()
    data class ErrorLoadingPhotos(val throwable: Throwable) : MovieDetailsState()
    object LoadingPhotos : MovieDetailsState()
}

sealed class MovieDetailsEvent {
    data class InitialEvent(val movie: Movie) : MovieDetailsEvent()
}