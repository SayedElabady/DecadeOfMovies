package com.swvltask.decadeofmovies.shared.ui

import androidx.appcompat.widget.SearchView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

fun SearchView.queryTextChangeEvents(): Subject<String> {
    val subject = PublishSubject.create<String>()
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onNext(query ?: "")
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText ?: "")
            return true
        }

    })
    return subject
}