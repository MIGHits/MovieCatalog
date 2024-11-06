package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.BanedMoviesMapper
import com.example.moviecatalog.data.data.mappers.DbFriendsMapper
import com.example.moviecatalog.data.data.mappers.DbGenresToDomain
import com.example.moviecatalog.data.data.mappers.DbUserMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.remote.dataSource.FavoriteMovieServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.DatabaseRepositoryImpl
import com.example.moviecatalog.data.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.mappers.GenreMapper
import com.example.moviecatalog.presentation.mappers.MovieMapper
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper

class FavoriteScreenViewModelFactory : ViewModelProvider.Factory {
    val dbRepository = DatabaseRepositoryImpl(
        DbUserMapper(),
        DbFriendsMapper(),
        DbGenresToDomain(),
        BanedMoviesMapper()
    )

    val userRepository = UserRepositoryImpl(
        PrefsTokenStorage,
        UserServiceProvider(),
        ProfileMapper(),
        ProfileDTOMapper()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteScreenViewModel(
            GenreMapper(),
            GetFavoriteGenresUseCase(dbRepository),
            GetUserProfileDataUseCase(
                userRepository
            ),
            ProfileUIMapper(),
            GetFavoriteMoviesUseCase(
                FavoriteMoviesRepositoryImpl(
                    PrefsTokenStorage,
                    FavoriteMovieServiceProvider()
                )
            ),
            MovieMapper(),
            DeleteFavoriteGenreUseCase(dbRepository)
        ) as T
    }
}