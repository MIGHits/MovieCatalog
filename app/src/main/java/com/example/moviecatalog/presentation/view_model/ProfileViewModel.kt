package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.domain.usecase.LogoutUseCase
import com.example.moviecatalog.domain.usecase.UpdateUserProfileAvatar
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileDataUseCase: GetUserProfileDataUseCase,
    private val updateUserProfileAvatar: UpdateUserProfileAvatar,
    private val logoutUseCase: LogoutUseCase,
    private val dateConverter: DateConverterUseCase = DateConverterUseCase(),
    private val profileMapper: ProfileUIMapper = ProfileUIMapper()
) : ViewModel() {
    private val _profile = MutableStateFlow(ProfileModelUI())
    val profile: StateFlow<ProfileModelUI> get() = _profile

    private val _time = MutableStateFlow(INITIAL_FIELD_STATE)
    val time:StateFlow<String> get() = _time

    fun getProfile() = viewModelScope.launch {
        _profile.value = profileMapper.map(getUserProfileDataUseCase())
        _profile.value =
            _profile.value.copy(
                birthDate =
                dateConverter.convertRemoteToUI(_profile.value.birthDate)
            )
    }

    fun updateUserProfile(avatarLink: String) = viewModelScope.launch {
        _profile.value = _profile.value.copy(
            avatarLink = avatarLink,
            birthDate = dateConverter.convertToIso(_profile.value.birthDate)
        )
        updateUserProfileAvatar(profileMapper.reverseMap(_profile.value))
    }

    fun getCurrentTime(){
        _time.value = dateConverter.getCurrentTime()
    }

    fun logout() = viewModelScope.launch{
        logoutUseCase()
    }
}