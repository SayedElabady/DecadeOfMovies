package com.swvltask.decadeofmovies.shared.store.model

class MoviePhotoMapper : IMapper<FlickerPicture, String> {
    override fun map(input: FlickerPicture): String =
        "http://farm${input.farm}.static.flickr.com/${input.server}/${input.id}_${input.secret}.jpg"

}