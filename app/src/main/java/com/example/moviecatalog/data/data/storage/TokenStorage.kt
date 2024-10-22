package com.example.moviecatalog.data.data.storage

interface TokenStorage {
    fun getToken():String
    fun saveToken(token:String)
    fun removeToken()
}