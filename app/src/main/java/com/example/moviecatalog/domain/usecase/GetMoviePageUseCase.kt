package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.domain.repository.MovieRepository

class GetMoviePageUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page:Int):MoviesPagedListModel{
        return  movieRepository.getMoviePage(page)
    }
}