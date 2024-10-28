package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.moviecatalog.data.data.remote.dataSource.AuthServiceProvider
import com.example.moviecatalog.data.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.usecase.LoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateLoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordUseCase

class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return LoginViewModel(
            ValidateLoginUseCase(),
            ValidatePasswordUseCase(),
            LoginUseCase(
                AuthRepositoryImpl(
                    PrefsTokenStorage, AuthServiceProvider()
                )
            )
        ) as T
    }
}