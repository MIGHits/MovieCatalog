package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.MovieCatalogApplication
import com.example.moviecatalog.data.data.local.entities.BanedFilms
import com.example.moviecatalog.data.data.local.entities.Database.UserDatabase
import com.example.moviecatalog.data.data.local.entities.FavoriteGenre
import com.example.moviecatalog.data.data.local.entities.Friends
import com.example.moviecatalog.data.data.local.entities.User
import com.example.moviecatalog.data.data.mappers.BanedMoviesMapper
import com.example.moviecatalog.data.data.mappers.DbFriendsMapper
import com.example.moviecatalog.data.data.mappers.DbGenresToDomain
import com.example.moviecatalog.data.data.mappers.DbUserMapper
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.data.data.mappers.UserMapper
import com.example.moviecatalog.domain.entity.BanedFilm
import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val userMapper: DbUserMapper,
    private val dbFriendsMapper: DbFriendsMapper,
    private val favoriteGenreMapper: DbGenresToDomain,
    private val banedMoviesMapper: BanedMoviesMapper
) : DatabaseRepository {
    private val db = UserDatabase.getDatabase(MovieCatalogApplication.instance)

    override suspend fun addUser(user: UserShortModel) {
        db.userDao().addUser(User(id = user.userId, nickname = user.nickName.toString()))
    }

    override suspend fun getUsers(): List<UserShortModel> {
        return userMapper.map(db.userDao().getUsers())
    }

    override suspend fun addFriend(friend: Friend, userId: String) {
        db.friendsDao().addFriend(
            Friends(
                userId = userId,
                nickname = friend.nickname,
                id = friend.id,
                avatar = friend.avatar
            )
        )
    }

    override suspend fun getFriendList(userId: String): Flow<List<Friend>> {
        return dbFriendsMapper.map(db.friendsDao().getUserFriends(userId))
    }

    override suspend fun deleteFriend(friendId: String, userId: String) {
        db.friendsDao().deleteFriend(friendId, userId)
    }

    override suspend fun addFavoriteGenre(genreModel: GenreModel, userId: String) {
        db.favoriteGenresDao()
            .addGenre(
                FavoriteGenre(
                    userId = userId,
                    id = genreModel.id,
                    name = genreModel.name.toString()
                )
            )
    }

    override suspend fun getFavoriteGenres(userId: String): Flow<List<GenreModel>> {
        return favoriteGenreMapper.map(db.favoriteGenresDao().getGenres(userId))
    }

    override suspend fun deleteFavoriteGenre(userId: String, genreId: String) {
        db.favoriteGenresDao().deleteGenre(genreId, userId)
    }

    override suspend fun banMovie(movie: BanedFilm, userId: String) {
        db.banMoviesDao()
            .banMovie(BanedFilms(userId = userId, id = movie.id, tittle = movie.tittle))
    }

    override suspend fun getMovieBlackList(userId: String): List<BanedFilm> {
        return banedMoviesMapper.map(db.banMoviesDao().getBlackList(userId))
    }

    override suspend fun unbanMovie(userId: String, movieId: String) {
        db.banMoviesDao().unbanMovie(userId, movieId)
    }

}