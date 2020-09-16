package com.swvltask.decadeofmovies.shared.store.sources.local

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.swvltask.decadeofmovies.shared.Constants.MOVIES_FILE_NAME
import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.model.MoviesResponse

class MoviesProvider(private val context: Context) : IMoviesProvider {
    override fun getAllMovies(): List<Movie> {
        Log.d("before Fetching", System.currentTimeMillis().toString())
        val moviesString = context.assets.open(MOVIES_FILE_NAME).bufferedReader().use { it.readText() }
        val response = Gson().fromJson(moviesString, MoviesResponse::class.java)
        Log.d("after Fetching", System.currentTimeMillis().toString())
        return response.movies
    }

}