package com.example.moviecatalog.data.data.storage

import android.content.Context
import android.util.Log
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.PREFS_NAME
import com.example.moviecatalog.common.Constants.TOKEN_KEY
import com.example.moviecatalog.MovieCatalogApplication
import com.example.moviecatalog.common.Constants.BEARER
import com.example.moviecatalog.data.data.remote.entities.Token

object PrefsTokenStorage : TokenStorage {
    private val context = MovieCatalogApplication.instance
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    override fun getToken(): Token {
        return Token(
            (BEARER + sharedPreferences.getString(TOKEN_KEY, INITIAL_FIELD_STATE))
        )
    }

    override fun saveToken(token: Token) {
        editor.putString(TOKEN_KEY, token.token).apply()
    }

    override fun removeToken() {
        editor.clear().apply()
    }

}