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
    const val GET_PROFILE:String = "api/account/profile"
    const val UPDATE_PROFILE:String = "api/account/profile"
    const val KP_TOKEN:String = "a38e7a3c-621b-4315-b84d-13135e2ef3b3"
    const val GET_MOVIES_BY_KEYWORD:String = "https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword"
    const val GET_MOVIE_RATINGS:String = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
    const val GET_MOVIE_DIRECTOR:String = "https://kinopoiskapiunofficial.tech/api/v1/persons"
}