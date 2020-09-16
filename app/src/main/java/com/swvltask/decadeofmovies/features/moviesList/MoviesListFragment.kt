package com.swvltask.decadeofmovies.features.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.swvltask.decadeofmovies.R
import com.swvltask.decadeofmovies.shared.ui.queryTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MoviesListFragment : Fragment() {

    val viewModel: MoviesListViewModel by viewModel()
    val sectionedAdapter = SectionedMoviesAdapter()
    val regularAdapter = MoviesAdapter()
    var disposable: Disposable? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movies_list, container, false)
        observeData()
        viewModel.processEvent(MoviesEvent.InitialEvent)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        disposable = search_sv.queryTextChangeEvents().debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.processEvent(MoviesEvent.SearchEvent(it.toString()))
            }
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
        disposable?.dispose()
    }

}