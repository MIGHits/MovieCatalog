package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.ReviewModel
import com.example.moviecatalog.presentation.entity.ReviewModelUI

class FullReviewMapper(private val userMapper: UserMapper = UserMapper()){
    fun map(fullReview: ReviewModel): ReviewModelUI {
        return ReviewModelUI(
            id = fullReview.id,
            rating = fullReview.rating,
            reviewText = fullReview.reviewText,
            isAnonymous = fullReview.isAnonymous,
            createDateTime = fullReview.createDateTime,
            author = userMapper.map(fullReview.author)
        )
    }

    fun map(reviewList:List<ReviewModel>):List<ReviewModelUI>{
        return reviewList.map { map(it) }
    }
}