package com.swvltask.decadeofmovies.shared.eventbus

import androidx.fragment.app.Fragment
import com.swvltask.decadeofmovies.shared.store.model.Movie


class Events {
    class Navigation {
        data class OnNavigationRequested(val fragment: Fragment, val tag: String? = null)

        class OnBackRequested
    }

    class ClickEvents {
        data class OnMovieClicked(val movie: Movie)
    }

}