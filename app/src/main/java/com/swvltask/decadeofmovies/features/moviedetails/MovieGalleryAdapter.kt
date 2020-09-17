package com.swvltask.decadeofmovies.features.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swvltask.decadeofmovies.R

class MovieGalleryAdapter(private val photos: List<String>) :
    RecyclerView.Adapter<MovieGalleryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_photo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var moviePhoto: ImageView? = null

        init {
            moviePhoto = view.findViewById(R.id.movie_photo_iv)
        }

        fun bind(moviePhotoUrl: String) {
            Picasso.get().load(moviePhotoUrl).placeholder(R.mipmap.ic_placeholder).into(moviePhoto)
        }
    }


}