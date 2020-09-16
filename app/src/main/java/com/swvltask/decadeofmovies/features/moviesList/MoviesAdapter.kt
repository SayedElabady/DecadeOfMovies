package com.swvltask.decadeofmovies.features.moviesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.shared.eventbus.EventBus
import com.swvltask.decadeofmovies.shared.eventbus.Events
import com.swvltask.decadeofmovies.shared.store.model.Movie

class MoviesAdapter(private val movies: MutableList<Movie> = mutableListOf()) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.view.setOnClickListener { EventBus.publish(Events.ClickEvents.OnMovieClicked(movies[position])) }
    }

    fun updateItems(movies: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MoviesDiffUtil(this.movies, movies))
        diffResult.dispatchUpdatesTo(this)
        this.movies.clear()
        this.movies.addAll(movies)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView? = null
        private var cast: TextView? = null
        private var rating: RatingBar? = null

        init {
            title = view.findViewById(R.id.title_tv)
            cast = view.findViewById(R.id.cast_tv)
            rating = view.findViewById(R.id.rating_rb)
        }

        fun bind(movie: Movie) {
            title?.text = movie.title
            cast?.text = movie.cast.joinToString(", ")
            rating?.rating = movie.rating.toFloat()
        }
    }

    inner class MoviesDiffUtil constructor(
        private val oldItems: List<Movie>,
        private val newItems: List<Movie>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition].title == oldItems[oldItemPosition].title
        }

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition] == oldItems[oldItemPosition]
        }
    }
}