package com.example.moviecatalog.data.data.local.entities.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecatalog.data.data.local.entities.FavoriteGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGenresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGenre(genre: FavoriteGenre)

    @Query("SELECT * FROM favorite_genres WHERE userId=:userId")
    fun getGenres(userId: String): Flow<List<FavoriteGenre>>

    @Query("DELETE FROM favorite_genres WHERE userId=:userId AND id=:genreId")
    suspend fun deleteGenre(genreId: String, userId: String)
}