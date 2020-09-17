package com.swvltask.decadeofmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swvltask.decadeofmovies.features.moviedetails.MovieDetailsEvent
import com.swvltask.decadeofmovies.features.moviedetails.MovieDetailsState
import com.swvltask.decadeofmovies.features.moviedetails.MovieDetailsViewModel
import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class MovieDetailsViewModelTest {

    @Mock
    val mockRepo = Mockito.mock(IMovieRepository::class.java)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun initialize() {
        viewModel =
            MovieDetailsViewModel(mockRepo, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `processEvent() with initial event then update state with photos`() {
        Mockito.`when`(mockRepo.getMoviePhotosUrls(mockMovies.first().title)).thenReturn(
            Single.just(
                mockPhotosUrls
            )
        )
        val expected = MovieDetailsState.OnPhotosReady(mockPhotosUrls)

        viewModel.processEvent(MovieDetailsEvent.InitialEvent(mockMovies.first()))

        Assert.assertEquals(viewModel.state.value, expected)
    }

    @Test
    fun `processEvent() with initial event then update state with error fetching`() {
        val error = Throwable()
        Mockito.`when`(mockRepo.getMoviePhotosUrls(mockMovies.first().title)).thenReturn(
            Single.error(error)
        )
        val expected = MovieDetailsState.ErrorLoadingPhotos(error)

        viewModel.processEvent(MovieDetailsEvent.InitialEvent(mockMovies.first()))

        Assert.assertEquals(viewModel.state.value, expected)
    }


}