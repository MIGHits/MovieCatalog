package com.example.moviecatalog.common

import android.provider.ContactsContract.CommonDataKinds.Email

object Constants{
    const val EMAIL_PATTERN = "[a-zA-Z0-9._-]{4,}+@[a-z]+\\.+[a-z]{2,}+"
    const val INITIAL_FIELD_STATE = ""
    const val EMPTY_FIELD_ERROR = "Поле не может быть пустым"
    const val UNIQUE_LOGIN_ERROR = "Указанный логин уже занят"
    const val LOGIN_EXCEPTION = "Неправильный логин или пароль"
    const val EXCEPTION_ERROR = "Проверьте соединение с сетью"
    const val LOGIN_ERROR = "Логин должен содержать не менее 2 латинских букв"
    const val PASSWORD_ERROR = "Минимальная длина пароля 6 символов"
    const val PASSWORD_CONFIRM_ERROR = "Пароли не совпадают"
    const val EMAIL_ERROR = "Неверное значение email"

    const val PREFS_NAME = "Token Storage"
    const val TOKEN_KEY = "token"

    const val MOVIE_TITTLE = "Название Фильма:"
    const val MOVIE_COUNTRY = "Страна Фильма"


    const val MIN_SWIPE_DISTANCE = -250
    const val MOVIE_STORY_DURATION = 5000L

    const val MORNING_GREETINGS = "Доброе утро,"
    const val DAY_GREETINGS = "Добрый день,"
    const val EVENING_GREETINGS = "Добрый вечер,"
    const val NIGHT_GREETINGS = "Доброй ночи,"

    const val MALE = 0
    const val FEMALE = 1
}