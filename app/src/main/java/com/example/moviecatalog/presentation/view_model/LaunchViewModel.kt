package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.EXCEPTION_ERROR
import com.example.moviecatalog.common.Constants.UNIQUE_LOGIN_ERROR
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.domain.usecase.IsUserLoginUseCase
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import com.example.moviecatalog.presentation.state.RegistrationState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LaunchViewModel(
    private val isUserLoginUseCase: IsUserLoginUseCase,
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val profileUIMapper: ProfileUIMapper
) : ViewModel() {
    private val _isUserLogin: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLogin: StateFlow<Boolean> get() = _isUserLogin

    private val _userProfile: MutableStateFlow<ProfileModelUI> = MutableStateFlow(ProfileModelUI())
    val userProfile: StateFlow<ProfileModelUI> get() = _userProfile

    init {
        checkUserToken()
    }

    fun checkUserToken() {
        _isUserLogin.value = isUserLoginUseCase()
    }

    fun getProfileData() = viewModelScope.launch {
        _userProfile.value = profileUIMapper.map(getUserProfileDataUseCase())
    }

}