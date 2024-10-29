package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.remote.dataSource.UserServiceProvider
import com.example.moviecatalog.data.data.repository.UserRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.domain.usecase.UpdateUserProfileAvatar

class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            GetUserProfileDataUseCase(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider()
                )
            ),
            UpdateUserProfileAvatar(
                UserRepositoryImpl(
                    PrefsTokenStorage,
                    UserServiceProvider()
                )
            )
        ) as T
    }
}