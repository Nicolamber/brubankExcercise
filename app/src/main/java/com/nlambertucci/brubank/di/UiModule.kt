package com.nlambertucci.brubank.di

import com.nlambertucci.brubank.data.remote.MoviesApiInterface
import com.nlambertucci.brubank.data.repository.MovieRepositoryImpl
import com.nlambertucci.brubank.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UiModule {

    @Provides
    fun providesMoviesRepository(api: MoviesApiInterface): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}