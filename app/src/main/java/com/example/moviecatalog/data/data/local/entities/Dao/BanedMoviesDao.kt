package com.example.moviecatalog.data.data.local.entities.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecatalog.data.data.local.entities.BanedFilms

@Dao
interface BanedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun banMovie(movie: BanedFilms)

    @Query("SELECT * FROM baned_movies WHERE userId =:userId")
    fun getBlackList(userId: String): List<BanedFilms>

    @Query("DELETE FROM baned_movies WHERE userId=:userId AND id=:movieId")
    suspend fun unbanMovie(userId: String, movieId: String)

}