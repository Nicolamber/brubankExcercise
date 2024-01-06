package com.nlambertucci.brubank

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SaveMovieAsFavoriteUseCaseTest {

    @Mock
    private lateinit var repository: FavoritesRepository

    private lateinit var saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        saveMovieAsFavoriteUseCase = SaveMovieAsFavoriteUseCase(repository)
    }

    @Test
    fun `test saveMovieAsFavorite should save the movie`() {

        val mockedMovie = Movie(
            "1", "Avengers: End game", "", "", "", ""
        )

        saveMovieAsFavoriteUseCase.saveMovieAsFavorite(mockedMovie)

        verify(repository).saveUserFavorites(mockedMovie)
    }
}