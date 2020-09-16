package com.swvltask.decadeofmovies.shared.store.model

import com.google.gson.annotations.SerializedName

data class FlickerPhotosResponse(@SerializedName("photos") val photos: PagedPhotos) {
    data class PagedPhotos(@SerializedName("photo") val photo: List<FlickerPicture>)
}