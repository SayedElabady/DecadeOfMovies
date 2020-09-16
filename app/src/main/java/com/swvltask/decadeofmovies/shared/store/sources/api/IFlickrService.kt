package com.swvltask.decadeofmovies.shared.store.sources.api

import com.swvltask.decadeofmovies.shared.store.model.FlickerPhotosResponse
import com.swvltask.decadeofmovies.shared.store.model.FlickerPicture
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IFlickrService {

    @GET("rest/?method=flickr.photos.search")
    fun getMoviePhotos(
        @Query("text") title: String,
        @Query("api_key") apiKey: String = "1587245ba14707d7b7180d9f05eac23f",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Single<FlickerPhotosResponse>
}