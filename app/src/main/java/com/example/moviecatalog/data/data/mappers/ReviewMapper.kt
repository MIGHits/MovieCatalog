package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.ReviewModelDTO
import com.example.moviecatalog.data.data.remote.entities.ReviewShortModelDTO
import com.example.moviecatalog.domain.entity.ReviewModel
import com.example.moviecatalog.domain.entity.ReviewShortModel

class ReviewMapper(private val userMapper:UserMapper = UserMapper()){
    fun map(review:ReviewShortModelDTO):ReviewShortModel{
        return ReviewShortModel(
            id = review.id,
            rating = review.rating
        )
    }

    fun map(reviewList:List<ReviewShortModelDTO>):List<ReviewShortModel>{
        return reviewList.map { map(it) }
    }

    fun mapFullReview(fullReview:ReviewModelDTO):ReviewModel{
        return ReviewModel(
            id = fullReview.id,
            rating = fullReview.rating,
            reviewText = fullReview.reviewText,
            isAnonymous = fullReview.isAnonymous,
            createDateTime = fullReview.createDateTime,
            author = userMapper.map(fullReview.author)
        )
    }

    fun mapFullReview(reviewList:List<ReviewModelDTO>):List<ReviewModel>{
        return reviewList.map { mapFullReview(it) }
    }
}