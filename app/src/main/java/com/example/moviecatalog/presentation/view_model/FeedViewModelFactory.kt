package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.remote.dataSource.MovieServiceProvider
import com.example.moviecatalog.data.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.mappers.GenreMapper
import com.example.moviecatalog.domain.mappers.MovieModelMapper
import com.example.moviecatalog.domain.mappers.PageInfoMapper
import com.example.moviecatalog.domain.repository.MovieRepository
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase

class FeedViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedViewModel(
            GetMoviePageUseCase(
            MovieRepositoryImpl(
            MovieServiceProvider(),
            MovieModelMapper(GenreMapper()),
            PageInfoMapper()
            ))) as T
    }
}