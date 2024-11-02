package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.MovieServicesRating
import com.example.moviecatalog.domain.repository.MovieRepository


class GetMovieRatingsUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(name:String,year: Int):MovieServicesRating{
       return movieRepository.getMovieRatings(name,year)
    }
}