package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.BanedMoviesMapper
import com.example.moviecatalog.data.data.mappers.DbFriendsMapper
import com.example.moviecatalog.data.data.mappers.DbGenresToDomain
import com.example.moviecatalog.data.data.mappers.DbUserMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.DatabaseRepositoryImpl
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.AddUserDbUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.domain.usecase.IsUserLoginUseCase
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper

class LaunchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LaunchViewModel(
            IsUserLoginUseCase(), GetUserProfileDataUseCase(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider(),
                    ProfileMapper(),
                    ProfileDTOMapper()
                )
            ), ProfileUIMapper(),
            AddUserDbUseCase(
                DatabaseRepositoryImpl(
                    DbUserMapper(),
                    DbFriendsMapper(),
                    DbGenresToDomain(),
                    BanedMoviesMapper()
                )
            )
        ) as T
    }
}