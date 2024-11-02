package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.MovieDetailsModel
import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMovieDetailsUseCase(private val movieRepository:MovieRepository) {
    suspend operator fun invoke(id:String): MovieDetailsModel {
        return movieRepository.getMovieDetails(id)
    }
}