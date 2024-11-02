package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.DirectorMapper
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.data.data.mappers.KinopoiskMovieMapper
import com.example.moviecatalog.data.data.mappers.MovieDetailsMapper
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.PageInfoMapper
import com.example.moviecatalog.data.data.remote.dataSource.MovieServiceProvider
import com.example.moviecatalog.data.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI

class MovieDetailsViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(
            GetMovieDetailsUseCase(
                MovieRepositoryImpl(
                    MovieServiceProvider(),
                    MovieModelMapper(GenreMapper()),
                    PageInfoMapper(),
                    MovieDetailsMapper(),
                    KinopoiskMovieMapper(),
                    DirectorMapper()
                )
            ),
            MovieDetailsMapperUI(),
            GetMovieRatingsUseCase(
                MovieRepositoryImpl(
                    MovieServiceProvider(),
                    MovieModelMapper(),
                    PageInfoMapper(),
                    MovieDetailsMapper(),
                    KinopoiskMovieMapper(),
                    DirectorMapper()
                )
            ),
            GetDirectorPosterUseCase(
                MovieRepositoryImpl(
                    MovieServiceProvider(),
                    MovieModelMapper(),
                    PageInfoMapper(),
                    MovieDetailsMapper(),
                    KinopoiskMovieMapper(),
                    DirectorMapper()
                )
            )
        ) as T
    }
}