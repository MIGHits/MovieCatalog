package com.example.moviecatalog.data.data.remote.dataSource

import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.ProfileModelDTO
import com.example.moviecatalog.data.data.remote.services.FavoriteMovieService
import com.example.moviecatalog.data.data.remote.services.UserService

class UserServiceProvider:UserService {
    private val userServiceProvider = retrofitProvider.create(UserService::class.java)

    override suspend fun getUserProfile(token: String): ProfileModelDTO {
       return userServiceProvider.getUserProfile(token)
    }

    override suspend fun updateUserProfile(token: String, profile: ProfileModelDTO) {
        userServiceProvider.updateUserProfile(token,profile)
    }
}