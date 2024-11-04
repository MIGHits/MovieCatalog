package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.DirectorMapper
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.data.data.mappers.KinopoiskMovieMapper
import com.example.moviecatalog.data.data.mappers.MovieDetailsMapper
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.PageInfoMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.mappers.UserReviewMapper
import com.example.moviecatalog.data.data.remote.dataSource.MovieServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.ReviewServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.data.repository.ReviewRepositoryImpl
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.EditReviewUseCase
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper

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
            ),
            AddReviewUseCase(
                ReviewRepositoryImpl(
                    PrefsTokenStorage, ReviewServiceProvider(),
                    UserReviewMapper()
                )
            ),
            EditReviewUseCase(
                ReviewRepositoryImpl(
                    PrefsTokenStorage, ReviewServiceProvider(),
                    UserReviewMapper()
                )
            ),
            DeleteReviewUseCase(
                ReviewRepositoryImpl(
                    PrefsTokenStorage, ReviewServiceProvider(),
                    UserReviewMapper()
                )
            ),
            com.example.moviecatalog.presentation.mappers.UserReviewMapper(),
            GetUserProfileDataUseCase(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider(),
                    ProfileMapper(),
                    ProfileDTOMapper()
                )
            ),
            ProfileUIMapper(),
            DateConverterUseCase()

        ) as T
    }
}