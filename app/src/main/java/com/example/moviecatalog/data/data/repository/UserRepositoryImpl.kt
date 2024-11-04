package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.ProfileDTOMapper
import com.example.moviecatalog.data.data.mappers.ProfileMapper
import com.example.moviecatalog.data.data.remote.services.FavoriteMovieService
import com.example.moviecatalog.data.data.remote.services.UserService
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.entity.ProfileModel
import com.example.moviecatalog.domain.repository.UserRepository

class UserRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val userApi: UserService,
    private val profileMapper: ProfileMapper,
    private val profileDTOMapper: ProfileDTOMapper
) : UserRepository {
    override suspend fun getProfile(): ProfileModel {
        return profileMapper.map(userApi.getUserProfile(tokenStorage.getToken().token))
    }

    override suspend fun updateProfile(profile: ProfileModel) {
        userApi.updateUserProfile(tokenStorage.getToken().token, profileDTOMapper.map(profile))
    }
}