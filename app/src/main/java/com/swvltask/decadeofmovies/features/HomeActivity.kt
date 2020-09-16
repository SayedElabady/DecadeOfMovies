package com.swvltask.decadeofmovies.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.features.moviesList.MoviesListFragment
import com.swvltask.decadeofmovies.shared.eventbus.EventBus
import com.swvltask.decadeofmovies.shared.eventbus.Events
import io.reactivex.disposables.Disposable

class HomeActivity : AppCompatActivity() {
    var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().add(R.id.container, MoviesListFragment()).commit()
        handleNavigation()
    }

    private fun handleNavigation() {
        disposable =
            EventBus.listen(Events.Navigation.OnNavigationRequested::class.java).subscribe {
                navigateTo(it.fragment, it.tag)
            }
    }

    private fun navigateTo(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack(tag)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}