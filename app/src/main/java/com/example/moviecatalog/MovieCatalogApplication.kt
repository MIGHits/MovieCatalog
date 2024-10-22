package com.example.moviecatalog

import android.app.Application

class MovieCatalogApplication:Application(){
    companion object {
        lateinit var instance: MovieCatalogApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}