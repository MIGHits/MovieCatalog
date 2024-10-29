package com.example.moviecatalog.presentation.mappers

import android.view.View
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.entity.MovieElementModel
import com.example.moviecatalog.domain.entity.ReviewShortModel
import com.example.moviecatalog.presentation.entity.ReviewShortModelPres
import kotlinx.coroutines.flow.StateFlow

class ReviewMapper {
    fun map(reviewList:List<ReviewShortModel>?):Double{
        var result = 0.0
        reviewList?.forEach { review -> result += review.rating }
        return (result / (reviewList?.size ?: 1))
    }
}