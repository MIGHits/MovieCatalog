package com.example.moviecatalog.data.data.local.entities.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecatalog.data.data.local.entities.Friends
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFriend(friend: Friends)

    @Query("SELECT * FROM user_friends WHERE userId =:userId")
    fun getUserFriends(userId: String): Flow<List<Friends>>

    @Query("DELETE FROM user_friends WHERE id =:friendId AND userId =:userId")
    suspend fun deleteFriend(friendId: String, userId: String)
}