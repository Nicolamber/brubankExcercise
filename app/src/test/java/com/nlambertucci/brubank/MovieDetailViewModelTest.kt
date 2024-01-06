package com.nlambertucci.brubank

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nlambertucci.brubank.domain.model.DetailDto
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.usecase.DeleteMovieFromFavoritesUseCase
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import com.nlambertucci.brubank.presentation.detail.viewmodel.MovieDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class MovieDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase

    @Mock
    lateinit var saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase

    @Mock
    lateinit var deleteMovieFromFavoritesUseCase: DeleteMovieFromFavoritesUseCase

    @Mock
    lateinit var observer: Observer<MovieDetailViewModel.DetailStatus>


    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = MovieDetailViewModel(
            getFavoritesMoviesUseCase,
            saveMovieAsFavoriteUseCase,
            deleteMovieFromFavoritesUseCase
        )

        viewModel.detailLiveStatus.observeForever(observer)
    }

    @Test
    fun `initView with valid movie should update status to Success`() {

        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )
        val favoriteMovies = listOf(movie)

        given(getFavoritesMoviesUseCase.getFavorites()).willReturn(favoriteMovies)

        viewModel.initView(movie)

        val detailDto = DetailDto(movie, true)

        verify(observer).onChanged(MovieDetailViewModel.DetailStatus.Loading)
        verify(observer).onChanged(MovieDetailViewModel.DetailStatus.Success(detailDto))
    }

    @Test
    fun `initView should show error status when something went wrong`() {

        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )
        val message = "Something went wrong"

        given(getFavoritesMoviesUseCase.getFavorites()).willThrow(NullPointerException(message))

        viewModel.initView(movie)

        verify(observer).onChanged(MovieDetailViewModel.DetailStatus.Loading)
        verify(observer).onChanged(MovieDetailViewModel.DetailStatus.Error(message))
    }

    @Test
    fun `setAsFavorite should save movie as favorite`() {
        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )

        viewModel.setAsFavorite(movie)

        verify(saveMovieAsFavoriteUseCase).saveMovieAsFavorite(movie)
    }

    @Test
    fun `removeMovieFromFavorites should remove movie as favorite`() {
        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )

        viewModel.removeMovieFromFavorites(movie)

        verify(deleteMovieFromFavoritesUseCase).deleteMovie(movie)
    }
}