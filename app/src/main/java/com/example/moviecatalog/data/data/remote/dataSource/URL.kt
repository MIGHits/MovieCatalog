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
    const val ADD_REVIEW:String = "api/movie/{movieId}/review/add"
    const val EDIT_REVIEW:String = "api/movie/{movieId}/review/{id}/edit"
    const val DELETE_REVIEW:String = "api/movie/{movieId}/review/{id}/delete"
    const val KP_TOKEN:String = "3e54fe5f-66b2-48cf-9717-927965cfa42e"
    const val GET_MOVIE_RATINGS:String = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
    const val GET_MOVIE_DIRECTOR:String = "https://kinopoiskapiunofficial.tech/api/v1/persons"
}