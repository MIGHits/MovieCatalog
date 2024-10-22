package com.example.moviecatalog.common

import android.provider.ContactsContract.CommonDataKinds.Email

object Constants{
    const val EMAIL_PATTERN = "[a-zA-Z0-9._-]{4,}+@[a-z]+\\.+[a-z]{2,}+"
    const val INITIAL_FIELD_STATE = ""
    const val EMPTY_FIELD_ERROR = "Поле не может быть пустым"
    const val LOGIN_ERROR = "Логин должен содержать не менее 2 латинских букв"
    const val PASSWORD_ERROR = "Минимальная длина пароля 6 символов"
    const val PASSWORD_CONFIRM_ERROR = "Пароли не совпадают"
    const val EMAIL_ERROR = "Неверное значение email"

    const val PREFS_NAME = "Token Storage"
    const val TOKEN_KEY = "token"

    const val MALE = 0
    const val FEMALE = 1
}