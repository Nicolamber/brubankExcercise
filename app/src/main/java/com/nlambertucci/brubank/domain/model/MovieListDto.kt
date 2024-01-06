package com.nlambertucci.brubank.domain.model

import androidx.annotation.Keep

@Keep
data class MovieListDto(
    val movies: List<Movie>,
    val favorites: List<Movie>?
)
