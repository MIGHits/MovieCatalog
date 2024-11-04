package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.LoginCredentialsMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.mappers.UserMapper
import com.example.moviecatalog.data.data.remote.dataSource.AuthServiceProvider
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.domain.usecase.LogoutUseCase
import com.example.moviecatalog.domain.usecase.UpdateUserProfileAvatar
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper

class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            GetUserProfileDataUseCase(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider(),
                    ProfileMapper(),
                    ProfileDTOMapper()
                )
            ),
            UpdateUserProfileAvatar(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider(),
                    ProfileMapper(),
                    ProfileDTOMapper()
                )
            ),
            LogoutUseCase(
                AuthRepositoryImpl(
                    PrefsTokenStorage,
                    AuthServiceProvider(),
                    UserMapper(),
                    LoginCredentialsMapper()
                )
            ),
            DateConverterUseCase(),
            ProfileUIMapper()
        ) as T
    }
}