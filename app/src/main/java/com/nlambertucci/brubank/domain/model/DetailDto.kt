package com.nlambertucci.brubank.domain.model

import androidx.annotation.Keep

@Keep
data class DetailDto(
    val movie: Movie,
    val isFavorite: Boolean
)
