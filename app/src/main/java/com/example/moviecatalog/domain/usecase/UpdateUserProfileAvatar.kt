package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.ProfileModel
import com.example.moviecatalog.domain.repository.UserRepository

class UpdateUserProfileAvatar (private val userRepository: UserRepository){
    suspend operator fun invoke(profile:ProfileModel){
        userRepository.updateProfile(profile)
    }
}