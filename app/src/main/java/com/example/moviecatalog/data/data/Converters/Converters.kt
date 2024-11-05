package com.example.moviecatalog.data.data.Converters

import androidx.room.TypeConverter
import com.example.moviecatalog.data.data.local.entities.Friends
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListString(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListFriend(value: List<Friends>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListFriend(value: String): List<Friends> {
        val listType = object : TypeToken<List<Friends>>() {}.type
        return Gson().fromJson(value, listType)
    }
}