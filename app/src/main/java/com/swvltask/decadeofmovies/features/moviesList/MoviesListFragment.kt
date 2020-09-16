package com.swvltask.decadeofmovies.features.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.features.moviedetails.MovieDetailsFragment
import com.swvltask.decadeofmovies.shared.eventbus.EventBus
import com.swvltask.decadeofmovies.shared.eventbus.Events
import com.swvltask.decadeofmovies.shared.ui.queryTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModel()
    private val sectionedAdapter = SectionedMoviesAdapter()
    private val regularAdapter = MoviesAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeData()
        viewModel.processEvent(MoviesEvent.InitialEvent)
    }

    private fun initListeners() {
        disposables.add(EventBus.listen(Events.ClickEvents.OnMovieClicked::class.java).subscribe {
            EventBus.publish(
                Events.Navigation.OnNavigationRequested(
                    MovieDetailsFragment.newInstance(
                        it.movie
                    )
                )
            )
        })

        disposables.add(search_sv.queryTextChangeEvents().debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.processEvent(MoviesEvent.SearchEvent(it.toString()))
            })
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MoviesViewState.MoviesResult -> {
                    movies_rv.apply {
                        adapter = regularAdapter
                        regularAdapter.updateItems(it.movies)
                        layoutManager = LinearLayoutManager(requireContext())
                        itemAnimator = DefaultItemAnimator()
                    }
                }
                is MoviesViewState.SearchMoviesResult -> {
                    movies_rv.adapter = sectionedAdapter
                    sectionedAdapter.updateItems(it.sections)
                }
                MoviesViewState.ResetMovies -> movies_rv.adapter = regularAdapter
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}