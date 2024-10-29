package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.ProfileModel

interface UserRepository {
    suspend fun getProfile():ProfileModel
    suspend fun updateProfile(profile:ProfileModel)
}