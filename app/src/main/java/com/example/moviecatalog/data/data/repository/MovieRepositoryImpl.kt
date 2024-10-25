package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import com.example.moviecatalog.data.data.remote.services.MovieService
import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.domain.mappers.MovieModelMapper
import com.example.moviecatalog.domain.mappers.PageInfoMapper
import com.example.moviecatalog.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieApi: MovieService,
    private val movieMapper:MovieModelMapper,
    private val pageMapper:PageInfoMapper):MovieRepository{

    override suspend fun getMoviePage(page: Int):MoviesPagedListModel{
        val dtoModel = movieApi.getMoviePage(page)
        return MoviesPagedListModel(
            movies = dtoModel.movies?.let { movieMapper.map(it) },
            pageInfo = pageMapper.map(dtoModel.pageInfo)
        )
    }

    override suspend fun getMovieDetails(id: String){

    }

}