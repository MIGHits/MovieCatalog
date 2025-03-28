package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.moviecatalog.data.data.mappers.LoginCredentialsMapper
import com.example.moviecatalog.data.data.mappers.UserMapper
import com.example.moviecatalog.data.data.remote.dataSource.AuthServiceProvider
import com.example.moviecatalog.data.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.data.storage.PrefsTokenStorage
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.RegisterUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateBirthDateUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateEmailUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateLoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateNameField
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordConfirmUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordUseCase

class RegistrationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RegistrationViewModel(
            ValidateLoginUseCase(),
            ValidateEmailUseCase(),
            ValidatePasswordUseCase(),
            ValidatePasswordConfirmUseCase(),
            ValidateBirthDateUseCase(),
            ValidateNameField(),
            DateConverterUseCase(),
            RegisterUseCase(
                AuthRepositoryImpl(
                    PrefsTokenStorage,
                    AuthServiceProvider(),
                    UserMapper(),
                    LoginCredentialsMapper()
                )
            )
        ) as T
    }
}