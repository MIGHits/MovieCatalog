package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.entities.ReviewModelDTO
import com.example.moviecatalog.data.data.remote.entities.ReviewShortModelDTO
import com.example.moviecatalog.data.data.remote.entities.UserReview
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @POST
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String,
        @Body reviewBody: UserReview
    )
}