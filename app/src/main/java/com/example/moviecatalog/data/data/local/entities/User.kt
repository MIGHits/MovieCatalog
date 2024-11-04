package com.example.moviecatalog.data.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class User(
    val id:String,
    val favoriteMovies:List<String>,
    val favoriteGenres:List<String>,
    val friends:List<Friends>
)
