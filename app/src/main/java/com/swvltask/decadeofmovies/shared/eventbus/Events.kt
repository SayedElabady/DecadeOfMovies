package com.swvltask.decadeofmovies.shared.eventbus

import androidx.fragment.app.Fragment


class Events {
    class Navigation {
        data class OnNavigationRequested(val fragment: Fragment, val tag: String? = null)
        data class OnOverlayRequested(val fragment: Fragment, val tag: String? = null)

        class OnBackRequested
    }

}