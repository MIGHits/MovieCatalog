package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.MovieRepository

class GetDirectorPosterUseCase(private val movieRepository: MovieRepository){
    suspend operator fun invoke(name:String):String?{
       return movieRepository.getDirector(name).directorPoster
    }
}