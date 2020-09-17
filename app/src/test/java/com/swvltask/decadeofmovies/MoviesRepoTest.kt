package com.swvltask.decadeofmovies

import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.repo.IMovieRepository
import com.swvltask.decadeofmovies.shared.store.repo.MovieRepository
import com.swvltask.decadeofmovies.shared.store.sources.api.IFlickrService
import com.swvltask.decadeofmovies.shared.store.sources.local.IMoviesProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MoviesRepoTest {

    @Mock
    val mockService = Mockito.mock(IFlickrService::class.java)

    @Mock
    val mockMoviesProvider = Mockito.mock(IMoviesProvider::class.java)


    lateinit var repo: IMovieRepository

    @Before
    fun initialize() {
        repo = MovieRepository(mockMoviesProvider, mockService, MockMapper())
    }

    @Test
    fun `getAllMovies() with available movies then return these movies`() {
        `when`(mockMoviesProvider.getAllMovies()).thenReturn(mockMovies)
        val expected = mockMovies

        val actual = repo.getAllMovies()

        assert(expected == actual)
    }

    @Test
    fun `getAllMovies() with empty movies then return empty list`() {
        `when`(mockMoviesProvider.getAllMovies()).thenReturn(emptyList())
        val expected = emptyList<Movie>()

        val actual = repo.getAllMovies()

        assert(expected == actual)
    }

    @Test
    fun `getMoviePhotosUrls() with available movie photos then return these photos`() {
        `when`(mockService.getMoviePhotos(mockMovies.first().title)).thenReturn(
            Single.just(
                mockPhotos
            )
        )
        val expected = mockPhotosUrls
        val testObserver = repo.getMoviePhotosUrls(mockMovies.first().title).test()

        repo.getMoviePhotosUrls(mockMovies.first().title)

        testObserver.assertValue(expected)
    }

    @Test
    fun `getMoviePhotosUrls() with error photos then return this error`() {
        val error = Throwable()
        `when`(mockService.getMoviePhotos(mockMovies.first().title)).thenReturn(
            Single.error(
                error
            )
        )
        val expected = error
        val testObserver = repo.getMoviePhotosUrls(mockMovies.first().title).test()

        repo.getMoviePhotosUrls(mockMovies.first().title)

        testObserver.assertError(expected)
    }
}