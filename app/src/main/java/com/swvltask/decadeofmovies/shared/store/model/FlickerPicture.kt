package com.swvltask.decadeofmovies.shared.store.model

import com.google.gson.annotations.SerializedName

/**
 * No need to get all of them, just what we need.
 *
 */
data class FlickerPicture(
    @SerializedName("id") val id: String,
    @SerializedName("server") val server: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("farm") val farm: Int
)