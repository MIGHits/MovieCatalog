package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.GET_PROFILE
import com.example.moviecatalog.data.data.remote.dataSource.URL.UPDATE_PROFILE
import com.example.moviecatalog.data.data.remote.entities.ProfileModelDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserService {
    @GET(GET_PROFILE)
    suspend fun getUserProfile(@Header("Authorization") token: String): ProfileModelDTO

    @PUT(UPDATE_PROFILE)
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body profile: ProfileModelDTO
    )
}