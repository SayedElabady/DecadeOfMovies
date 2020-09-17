package com.swvltask.decadeofmovies

import com.swvltask.decadeofmovies.shared.store.model.FlickerPhotosResponse
import com.swvltask.decadeofmovies.shared.store.model.FlickerPicture
import com.swvltask.decadeofmovies.shared.store.model.IMapper
import com.swvltask.decadeofmovies.shared.store.model.Movie

val mockMovies = listOf(
    Movie(
        title = "Movie1",
        cast = listOf("Actress1"),
        genres = listOf("genre1"),
        rating = 2,
        year = 2020
    ),
    Movie(
        title = "Movie2",
        cast = listOf("Actress2"),
        genres = listOf("genre2"),
        rating = 3,
        year = 2020
    )
)

val mockPhotos = FlickerPhotosResponse(
    FlickerPhotosResponse.PagedPhotos(
        listOf(
            FlickerPicture(
                "id1",
                "server1",
                "secret1",
                66
            )
        )
    )
)
val mockPhotosUrls = listOf("id1server1")

class MockMapper : IMapper<FlickerPicture, String> {
    override fun map(input: FlickerPicture): String {
        return input.id + input.server
    }

}