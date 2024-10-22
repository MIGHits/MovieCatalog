package com.example.moviecatalog.data.data.storage

import android.content.Context
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.PREFS_NAME
import com.example.moviecatalog.common.Constants.TOKEN_KEY
import com.example.moviecatalog.MovieCatalogApplication

class PrefsTokenStorage():TokenStorage{
    private val context = MovieCatalogApplication.instance
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    override fun getToken(): String {
      return sharedPreferences.getString(TOKEN_KEY, INITIAL_FIELD_STATE)?: INITIAL_FIELD_STATE
    }

    override fun saveToken(token: String) {
        editor.putString(TOKEN_KEY,token).apply()
    }

    override fun removeToken() {
        editor.remove(TOKEN_KEY)
    }

}