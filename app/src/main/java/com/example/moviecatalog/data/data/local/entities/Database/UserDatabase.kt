package com.example.moviecatalog.data.data.local.entities.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviecatalog.data.data.local.entities.BanedFilms
import com.example.moviecatalog.data.data.local.entities.Dao.BanedMoviesDao
import com.example.moviecatalog.data.data.local.entities.Dao.FavoriteGenresDao
import com.example.moviecatalog.data.data.local.entities.Dao.FriendsDao
import com.example.moviecatalog.data.data.local.entities.Dao.UserDao
import com.example.moviecatalog.data.data.local.entities.FavoriteGenre
import com.example.moviecatalog.data.data.local.entities.Friends
import com.example.moviecatalog.data.data.local.entities.User

@Database(
    entities = [User::class, FavoriteGenre::class, BanedFilms::class, Friends::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun friendsDao(): FriendsDao
    abstract fun banMoviesDao(): BanedMoviesDao
    abstract fun favoriteGenresDao(): FavoriteGenresDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    name = "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}