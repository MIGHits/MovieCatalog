package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.UserReviewDTO
import com.example.moviecatalog.domain.entity.UserReview

class UserReviewMapper {
    fun map(reviewBody: UserReview): UserReviewDTO {
        return  UserReviewDTO(
            reviewText = reviewBody.reviewText,
            rating = reviewBody.rating,
            isAnonymous = reviewBody.isAnonymous
        )
    }
}