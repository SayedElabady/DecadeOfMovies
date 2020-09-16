package com.swvltask.decadeofmovies.features.moviesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.shared.store.model.Movie
import com.swvltask.decadeofmovies.shared.store.model.MoviesSection
import org.zakariya.stickyheaders.SectioningAdapter

class SectionedMoviesAdapter(private val sections: MutableList<MoviesSection> = mutableListOf()) :
    SectioningAdapter() {

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean = false
    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean = true

    override fun onCreateHeaderViewHolder(
        parent: ViewGroup?,
        headerUserType: Int
    ): SectioningAdapter.HeaderViewHolder {
        return HeaderViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_year_header, parent, false)
        )
    }

    override fun onBindHeaderViewHolder(
        viewHolder: SectioningAdapter.HeaderViewHolder?,
        sectionIndex: Int,
        headerUserType: Int
    ) {
        (viewHolder as HeaderViewHolder).bind(sections[sectionIndex])
    }

    fun updateItems(sections: List<MoviesSection>) {
        this.sections.clear()
        this.sections.addAll(sections)
        notifyAllSectionsDataSetChanged()
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return this.sections[sectionIndex].movies.size
    }

    override fun onCreateItemViewHolder(
        parent: ViewGroup?,
        itemUserType: Int
    ): SectioningAdapter.ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent?.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindItemViewHolder(
        viewHolder: SectioningAdapter.ItemViewHolder?,
        sectionIndex: Int,
        itemIndex: Int,
        itemUserType: Int
    ) {
        (viewHolder as ItemViewHolder?)?.bind(sections[sectionIndex].movies[itemIndex])
    }

    inner class ItemViewHolder(val view: View) : SectioningAdapter.ItemViewHolder(view) {
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

    inner class HeaderViewHolder(val view: View) : SectioningAdapter.HeaderViewHolder(view) {
        private var year: TextView? = null

        init {
            year = view.findViewById(R.id.year_tv)
        }

        fun bind(section: MoviesSection) {
            year?.text = section.year.toString()
        }
    }

    inner class MovieSectionDiffUtil constructor(
        private val oldItems: List<MoviesSection>,
        private val newItems: List<MoviesSection>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition].year == oldItems[oldItemPosition].year
        }

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition] == oldItems[oldItemPosition]
        }
    }
}