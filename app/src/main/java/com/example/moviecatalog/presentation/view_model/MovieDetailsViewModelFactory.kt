package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.BanedMoviesMapper
import com.example.moviecatalog.data.data.mappers.DbGenresToDomain
import com.example.moviecatalog.data.data.mappers.DirectorMapper
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.data.data.mappers.KinopoiskMovieMapper
import com.example.moviecatalog.data.data.mappers.MovieDetailsMapper
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.PageInfoMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.mappers.UserReviewMapper
import com.example.moviecatalog.data.data.mappers.DbFriendsMapper
import com.example.moviecatalog.data.data.mappers.DbUserMapper
import com.example.moviecatalog.data.data.remote.dataSource.FavoriteMovieServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.MovieServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.ReviewServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.DatabaseRepositoryImpl
import com.example.moviecatalog.data.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.data.repository.ReviewRepositoryImpl
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.AddFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.AddFavoritegenreUseCase
import com.example.moviecatalog.domain.usecase.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.AddUserDbUseCase
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.DeleteFavoriteMovieUseCase
import com.example.moviecatalog.domain.usecase.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.EditReviewUseCase
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetFriendsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI
import com.example.moviecatalog.presentation.mappers.MovieMapper
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import com.example.moviecatalog.presentation.mappers.UserMapper

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
            DateConverterUseCase(),
            com.example.moviecatalog.presentation.mappers.GenreMapper(),
            AddUserDbUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ),
            UserMapper(),
            AddFriendUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ),
            GetFriendsUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ),
            AddFavoritegenreUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ),
            GetFavoriteGenresUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ),
            DeleteFavoriteGenreUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(), DbGenresToDomain
                        (), BanedMoviesMapper
                        ()
                )
            ), AddFavoriteMoviesUseCase(
                FavoriteMoviesRepositoryImpl(
                    PrefsTokenStorage,
                    FavoriteMovieServiceProvider()
                )
            ),
            DeleteFavoriteMovieUseCase(
                FavoriteMoviesRepositoryImpl(
                    PrefsTokenStorage,
                    FavoriteMovieServiceProvider()
                )
            ),
            GetFavoriteMoviesUseCase(
                FavoriteMoviesRepositoryImpl(
                    PrefsTokenStorage,
                    FavoriteMovieServiceProvider()
                )
            ),
            MovieMapper()

        ) as T
    }
}