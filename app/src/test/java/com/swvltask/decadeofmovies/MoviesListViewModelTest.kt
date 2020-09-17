package com.swvltask.decadeofmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swvltask.decadeofmovies.features.moviesList.MoviesEvent
import com.swvltask.decadeofmovies.features.moviesList.MoviesListViewModel
import com.swvltask.decadeofmovies.features.moviesList.MoviesViewState
import com.swvltask.decadeofmovies.shared.store.model.MovieSection
import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MoviesListViewModelTest {

    @Mock
    val mockRepo = Mockito.mock(IMovieRepository::class.java)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MoviesListViewModel

    @Before
    fun initialize() {
        viewModel = MoviesListViewModel(mockRepo)
    }

    @Test
    fun `processEvent() with initial Event then update state with available movies`() {
        `when`(mockRepo.getAllMovies()).thenReturn(mockMovies)
        val expected = MoviesViewState.MoviesResult(mockMovies)

        viewModel.processEvent(MoviesEvent.InitialEvent)

        assertEquals(viewModel.state.value, expected)
    }

    @Test
    fun `processEvent() with search title then update state with coresponding movie`() {
        `when`(mockRepo.getAllMovies()).thenReturn(mockMovies)
        val movieSections =
            listOf(MovieSection(mockMovies.first().year, listOf(mockMovies.first())))
        val expected = MoviesViewState.SearchMoviesResult(movieSections)

        viewModel.processEvent(MoviesEvent.InitialEvent)
        viewModel.processEvent(MoviesEvent.SearchEvent(mockMovies.first().title))

        assertEquals(viewModel.state.value, expected)
    }

    @Test
    fun `processEvent() with empty search title then update state with reset movies`() {
        `when`(mockRepo.getAllMovies()).thenReturn(mockMovies)
        val expected = MoviesViewState.ResetMovies

        viewModel.processEvent(MoviesEvent.InitialEvent)
        viewModel.processEvent(MoviesEvent.SearchEvent(""))

        assertEquals(viewModel.state.value, expected)
    }

    @Test
    fun `processEvent() with not found title then update state with empty results`() {
        `when`(mockRepo.getAllMovies()).thenReturn(mockMovies)
        val movieSections = listOf<MovieSection>()
        val expected = MoviesViewState.SearchMoviesResult(movieSections)

        viewModel.processEvent(MoviesEvent.InitialEvent)
        viewModel.processEvent(MoviesEvent.SearchEvent("#######"))

        assertEquals(viewModel.state.value, expected)
    }
}