package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.ADD_REVIEW
import com.example.moviecatalog.data.data.remote.dataSource.URL.DELETE_REVIEW
import com.example.moviecatalog.data.data.remote.dataSource.URL.EDIT_REVIEW
import com.example.moviecatalog.data.data.remote.entities.UserReviewDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewService {
    @POST(ADD_REVIEW)
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String,
        @Body reviewBody: UserReviewDTO
    )

    @PUT(EDIT_REVIEW)
    suspend fun updateReview(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String,
        @Path("id") id: String,
        @Body reviewBody: UserReviewDTO
    )

    @DELETE(DELETE_REVIEW)
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String,
        @Path("id") id: String,
    )
}