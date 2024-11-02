package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.DirectorMapper
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.data.data.mappers.KinopoiskMovieMapper
import com.example.moviecatalog.data.data.mappers.MovieDetailsMapper
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.PageInfoMapper
import com.example.moviecatalog.data.data.remote.dataSource.FavoriteMovieServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.MovieServiceProvider
import com.example.moviecatalog.data.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase

class MovieScreenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieScreenViewModel(
            GetMoviePageUseCase(
                MovieRepositoryImpl(
                    MovieServiceProvider(),
                    MovieModelMapper(),
                    PageInfoMapper(),
                    MovieDetailsMapper(),
                    KinopoiskMovieMapper(),
                    DirectorMapper()
                )
            ),
            GetFavoriteMoviesUseCase(
                FavoriteMoviesRepositoryImpl(
                    PrefsTokenStorage,
                    FavoriteMovieServiceProvider()
                )
            )
        ) as T
    }
}