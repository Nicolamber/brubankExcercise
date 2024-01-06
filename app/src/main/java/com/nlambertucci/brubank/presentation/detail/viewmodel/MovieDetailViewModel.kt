package com.nlambertucci.brubank.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nlambertucci.brubank.domain.model.DetailDto
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.usecase.DeleteMovieFromFavoritesUseCase
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase,
    private val deleteMovieFromFavoritesUseCase: DeleteMovieFromFavoritesUseCase
) : ViewModel() {

    sealed class DetailStatus {
        object Loading : DetailStatus()
        data class Success(val detailDto: DetailDto) : DetailStatus()
        data class Error(val message: String) : DetailStatus()
    }

    private val detailStatus = MutableLiveData<DetailStatus>()
    val detailLiveStatus: LiveData<DetailStatus> = detailStatus

    fun initView(movie: Movie?) {
        movie ?: return
        parseData(movie)
    }

    private fun parseData(movie: Movie) {
        detailStatus.value = DetailStatus.Loading
        try {
            val favMovies = getFavoritesMoviesUseCase.getFavorites() ?: listOf()
            val isFavorite = favMovies.contains(movie)
            val detailDto = DetailDto(
                movie = movie,
                isFavorite = isFavorite
            )
            detailStatus.value = DetailStatus.Success(detailDto)
            return
        } catch (e: Exception) {
            detailStatus.value = DetailStatus.Error(e.message ?: "")
        }

    }

    fun setAsFavorite(movie: Movie) {
        saveMovieAsFavorite(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavorites(movie)
    }

    private fun deleteMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavoritesUseCase.deleteMovie(movie)
    }

    private fun saveMovieAsFavorite(movie: Movie) {
        saveMovieAsFavoriteUseCase.saveMovieAsFavorite(movie)
    }
}
