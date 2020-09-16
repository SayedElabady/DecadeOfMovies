package com.swvltask.decadeofmovies.features.moviedetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.shared.store.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance(movie: Movie) = MovieDetailsFragment().apply {
            this.movie = movie
        }
    }

    private var movie: Movie? = null
    private val viewModel: MovieDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovie()
        observeData()
        viewModel.processEvent(MovieDetailsEvent.InitialEvent(movie!!))
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MovieDetailsState.OnPhotosReady -> {
                    gallery_pb.visibility = GONE
                    gallery_rv.apply {
                        adapter = MovieGalleryAdapter(it.photosUrls)
                        layoutManager = GridLayoutManager(requireContext(), 2)
                    }
                }
                is MovieDetailsState.ErrorLoadingPhotos -> {
                    gallery_pb.visibility = GONE
                    Toast.makeText(
                        requireContext(),
                       "An error has occured, ${it.throwable.localizedMessage} please try again later." ,
                        Toast.LENGTH_SHORT
                    ).show();
                }
                MovieDetailsState.LoadingPhotos -> gallery_pb.visibility = VISIBLE
            }
        })
    }

    private fun bindMovie() {
        title_tv.text = movie?.title
        rating_rb.rating = movie?.rating?.toFloat()!!
        genre_value_tv.text =
            if (movie?.genres.isNullOrEmpty()) "NONE" else movie?.genres?.joinToString(", ")
        year_tv.text = "(${movie?.year.toString()})"
        cast_value_tv.text =
            if (movie?.cast.isNullOrEmpty()) "NONE" else movie?.cast?.joinToString(", ")
    }
}