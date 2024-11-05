package com.example.moviecatalog.data.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class User(
    @PrimaryKey val id: String,
    val nickname:String
)
