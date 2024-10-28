package com.example.moviecatalog.presentation.entity

import android.view.View
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.MALE

data class RegistrationCredentials(
    val userName: String = INITIAL_FIELD_STATE,
    val loginError: String? = null,
    val loginErrorView: Int = View.GONE,

    val name: String = INITIAL_FIELD_STATE,
    val nameError: String? = null,
    val nameErrorView: Int = View.GONE,

    val password: String = INITIAL_FIELD_STATE,
    val passwordError: String? = null,
    val passwordErrorView: Int = View.GONE,

    val passwordConfirm: String = INITIAL_FIELD_STATE,
    val passwordConfirmError: String? = null,
    val passwordConfirmErrorView: Int = View.GONE,

    val email: String = INITIAL_FIELD_STATE,
    val emailError: String? = null,
    val emailErrorView: Int = View.GONE,

    val birthDate: String = INITIAL_FIELD_STATE,
    val birthDateError: String? = null,
    val birthDayErrorView: Int = View.GONE,

    val gender: Int = MALE,
    val exceptionError: String? = null
)
