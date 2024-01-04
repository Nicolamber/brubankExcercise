package com.nlambertucci.brubank.presentation.actions

import com.nlambertucci.brubank.domain.model.Movie

interface AdapterActionsInterface {
    fun onContainerClicked(movie: Movie)
    fun onAddMovieClicked(movie: Movie)
    fun onRemoveMovieClicked(movie: Movie)
}