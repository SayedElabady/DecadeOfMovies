package com.swvltask.decadeofmovies.features.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.SortedList
import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.model.MoviesSection
import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository

class MoviesListViewModel(private val repo: IMovieRepository) : ViewModel() {

    private val _moviesSource = mutableListOf<Movie>()
    private val _state = MutableLiveData<MoviesViewState>()

    val state: LiveData<MoviesViewState> = _state

    private fun initData() {
        with(repo.getAllMovies()) {
            _moviesSource.addAll(this)
            _state.value = MoviesViewState.MoviesResult(this)
        }
    }

    fun processEvent(event: MoviesEvent) {
        when (event) {
            MoviesEvent.InitialEvent -> initData()
            is MoviesEvent.SearchEvent -> search(event.searchQuery)
        }
    }

    private fun search(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            _state.value = MoviesViewState.ResetMovies
        } else {
            _state.value =
                MoviesViewState.SearchMoviesResult(
                    _moviesSource.asSequence().filter { it.title.toLowerCase().contains(searchQuery.toLowerCase())  }
                        .sortedByDescending { it.rating }.take(5).groupBy {
                            it.year
                        }.map {
                            MoviesSection(it.key, it.value)
                        }.toList()
                )
        }
    }

}

sealed class MoviesViewState() {
    data class MoviesResult(val movies: List<Movie>) : MoviesViewState()
    data class SearchMoviesResult(val sections: List<MoviesSection>) : MoviesViewState()
    object ResetMovies : MoviesViewState()
}

sealed class MoviesEvent() {
    object InitialEvent : MoviesEvent()
    data class SearchEvent(val searchQuery: String) : MoviesEvent()
}