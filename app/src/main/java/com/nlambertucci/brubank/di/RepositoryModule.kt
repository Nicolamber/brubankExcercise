package com.nlambertucci.brubank.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.data.repository.FavoritesRepositoryImpl
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesFavoritesRepository(sharedPref: SharedPreferences): FavoritesRepository {
        return FavoritesRepositoryImpl(sharedPref)
    }

    @Provides
    @Singleton
    fun providesGetFavoritesUseCase(repository: FavoritesRepository): GetFavoritesMoviesUseCase {
        return GetFavoritesMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesSaveFavoritesUseCase(repository: FavoritesRepository): SaveMovieAsFavoriteUseCase {
        return SaveMovieAsFavoriteUseCase(repository)
    }
}