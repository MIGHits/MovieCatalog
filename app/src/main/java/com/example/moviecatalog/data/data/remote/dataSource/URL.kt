package com.example.moviecatalog.data.data.remote.dataSource

object URL {
    const val BASE_URL:String = "https://react-midterm.kreosoft.space/"
    const val REGISTER:String = "api/account/register"
    const val LOGIN:String = "api/account/login"
    const val LOGOUT:String = "api/account/logout"
    const val MOVIE_PAGE:String = "api/movies/{page}"
    const val MOVIE_DETAILS:String = "api/movies/details/{id}"
    const val FAVORITES:String = "api/favorites"
    const val ADD_FAVORITES:String = "api/favorites/{id}/add"
    const val REMOVE_FAVORITES:String = "api/favorites/{id}/delete"
}