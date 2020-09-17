package com.swvltask.decadeofmovies.shared.store.model

interface IMapper<I, O> {
    fun map(input: I): O

    fun mapList(inputs: List<I>): List<O> {
        return inputs.map { map(it) }
    }
}
