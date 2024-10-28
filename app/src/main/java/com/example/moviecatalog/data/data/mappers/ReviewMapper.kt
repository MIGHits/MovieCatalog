package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.ReviewShortModelDTO
import com.example.moviecatalog.domain.entity.ReviewModel
import com.example.moviecatalog.domain.entity.ReviewShortModel

class ReviewMapper {
    fun map(review:ReviewShortModelDTO):ReviewShortModel{
        return ReviewShortModel(
            id = review.id,
            rating = review.rating
        )
    }

    fun map(reviewList:List<ReviewShortModelDTO>):List<ReviewShortModel>{
        return reviewList.map { map(it) }
    }
}