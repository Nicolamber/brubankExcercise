package com.nlambertucci.brubank.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MoviesResponse (
    @SerializedName("page")
    val currentPage: Int,
    @SerializedName("results")
    val movies: List<Movie>?,
    @SerializedName("total_pages")
    val pages: Int
)