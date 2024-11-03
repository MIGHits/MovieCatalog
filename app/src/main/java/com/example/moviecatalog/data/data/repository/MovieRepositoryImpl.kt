package com.example.moviecatalog.data.data.repository

import android.util.Log
import com.example.moviecatalog.data.data.mappers.DirectorMapper
import com.example.moviecatalog.data.data.mappers.KinopoiskMovieMapper
import com.example.moviecatalog.data.data.mappers.MovieDetailsMapper
import com.example.moviecatalog.data.data.remote.services.MovieService
import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.mappers.PageInfoMapper
import com.example.moviecatalog.domain.entity.MovieDetailsModel
import com.example.moviecatalog.domain.entity.MovieDirector
import com.example.moviecatalog.domain.entity.MovieServicesRating
import com.example.moviecatalog.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieApi: MovieService,
    private val movieMapper: MovieModelMapper,
    private val pageMapper: PageInfoMapper,
    private val detailsMapper: MovieDetailsMapper,
    private val kinopoiskMovieMapper: KinopoiskMovieMapper,
    private val movieDirectorMapper: DirectorMapper
) : MovieRepository {

    override suspend fun getMoviePage(page: Int): MoviesPagedListModel {
        val dtoModel = movieApi.getMoviePage(page)
        return MoviesPagedListModel(
            movies = dtoModel.movies?.let { movieMapper.map(it) },
            pageInfo = pageMapper.map(dtoModel.pageInfo)
        )
    }

    override suspend fun getMovieDetails(id: String): MovieDetailsModel {
        val dtoModel = movieApi.getMovieDetails(id)
        return detailsMapper.map(dtoModel)
    }

    override suspend fun getMovieRatings(name: String, year: Int): MovieServicesRating {
        val dtoModel = movieApi.getMovieByFilters(keyword = name, year = year)
        return kinopoiskMovieMapper.map(dtoModel)
    }

    override suspend fun getDirector(name: String): MovieDirector {
        val dtoModel = movieApi.getDirector(name = name)
        return movieDirectorMapper.map(dtoModel)
    }

}