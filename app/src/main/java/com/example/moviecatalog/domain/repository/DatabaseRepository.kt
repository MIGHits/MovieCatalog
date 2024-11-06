package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.BanedFilm
import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.entity.UserShortModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addUser(user: UserShortModel)

    suspend fun getUsers(): List<UserShortModel>

    suspend fun addFriend(friend: Friend, userId: String)

    suspend fun getFriendList(userId: String):Flow<List<Friend>>

    suspend fun deleteFriend(friendId: String, userId: String)

    suspend fun addFavoriteGenre(genreModel: GenreModel, userId: String)

    suspend fun getFavoriteGenres(userId: String): Flow<List<GenreModel>>

    suspend fun deleteFavoriteGenre(userId: String, genreId: String)

    suspend fun banMovie(movie: BanedFilm, userId: String)

    suspend fun getMovieBlackList(userId: String): List<BanedFilm>

    suspend fun unbanMovie(userId: String, movieId: String)
}