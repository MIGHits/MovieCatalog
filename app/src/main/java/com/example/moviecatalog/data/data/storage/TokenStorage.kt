package com.example.moviecatalog.data.data.storage

import com.example.moviecatalog.data.data.remote.entities.Token

interface TokenStorage {
    fun getToken(): Token
    fun saveToken(token: Token)
    fun removeToken()
}