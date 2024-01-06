package com.nlambertucci.brubank.presentation.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nlambertucci.brubank.domain.model.*
import com.nlambertucci.brubank.domain.repository.MovieRepository
import com.nlambertucci.brubank.domain.usecase.DeleteMovieFromFavoritesUseCase
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase,
    private val deleteMovieFromFavoritesUseCase: DeleteMovieFromFavoritesUseCase

) : ViewModel() {
    sealed class MovieStatus {
        object Loading : MovieStatus()
        data class Success(val moviesDto: MovieListDto) : MovieStatus()
        data class Error(val message: String?) : MovieStatus()
        data class SearchSuccess(val searchResult: SearchDto) : MovieStatus()
    }

    private val status = MutableLiveData<MovieStatus>()
    val moviesLiveStatus: LiveData<MovieStatus> = status
    private var favoritesMovies: List<Movie>? = null

    init {
        favoritesMovies = getFavoritesMoviesUseCase.getFavorites()
    }

    fun initView() {
        fetchMovies()
    }

    private fun fetchMovies() {
        status.value = MovieStatus.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMovies()
            }.onSuccess { response ->
                response.body()?.movies?.let {
                    val moviesDto = MovieListDto(
                        it,
                        getFavoritesMoviesUseCase.getFavorites()
                    )
                    status.value = MovieStatus.Success(moviesDto)
                    return@launch
                }
            }.onFailure {
                status.value = MovieStatus.Error(it.message)
                return@launch
            }
        }
    }

    fun searchMovie(query: String) {
        getMovieByQuery(query)
    }

    private fun getMovieByQuery(query: String) {
        status.value = MovieStatus.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMovieByName(query)
            }.onSuccess { response ->
                response.body()?.movies?.let {
                    status.value = MovieStatus.SearchSuccess(
                        SearchDto(
                            it,
                            favoritesMovies
                        )
                    )
                }
                return@launch
            }.onFailure {
                status.value = MovieStatus.Error(it.message)
                return@launch
            }
        }
    }

    fun setAsFavorite(movie: Movie) {
        saveMovieAsFavorite(movie)
    }

    private fun saveMovieAsFavorite(movie: Movie) {
        saveMovieAsFavoriteUseCase.saveMovieAsFavorite(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavorites(movie)
    }

    private fun deleteMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavoritesUseCase.deleteMovie(movie)
    }

}
