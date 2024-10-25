package com.example.moviecatalog.presentation.view_model

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.EXCEPTION_ERROR
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.UNIQUE_LOGIN_ERROR
import com.example.moviecatalog.domain.usecase.DateConverter
import com.example.moviecatalog.domain.usecase.RegisterUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateBirthDateUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateEmailUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateLoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordConfirmUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateNameField
import com.example.moviecatalog.presentation.entity.RegistrationCredentials
import com.example.moviecatalog.presentation.entity.UserRegisterUIModel
import com.example.moviecatalog.presentation.state.ButtonState
import com.example.moviecatalog.presentation.state.RegistrationState
import com.example.moviecatalog.presentation.state.RegistrationUIState
import com.example.moviecatalog.presentation.view.AppNavigationActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegistrationViewModel(private val validateLoginUseCase:ValidateLoginUseCase,
                            private val validateEmailUseCase:ValidateEmailUseCase,
                            private val validatePasswordUseCase:ValidatePasswordUseCase,
                            private val validatePasswordConfirmUseCase:ValidatePasswordConfirmUseCase,
                            private val validateBirthDateUseCase:ValidateBirthDateUseCase,
                            private val validateNameField:ValidateNameField,
                            private val dateConverter: DateConverter,
                            private val registerUseCase: RegisterUseCase
    ):ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState.INITIAL)
    val registrationState:StateFlow<RegistrationState> get() = _registrationState

    private val _registration = MutableStateFlow(
        RegistrationCredentials())
    val registration:StateFlow<RegistrationCredentials> get() =  _registration

    private val _registrationUI = MutableStateFlow(RegistrationUIState())
    val registrationUI:StateFlow<RegistrationUIState> get() = _registrationUI

    private val _registrationValid = MutableStateFlow(ButtonState())
    val registrationValid:StateFlow<ButtonState>get() = _registrationValid

    fun changeUIState(element:String){
        when(element){
            "login"->if(_registration.value.userName == INITIAL_FIELD_STATE){
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(loginIconVisibility = View.INVISIBLE)
            }else{
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(loginIconVisibility = View.VISIBLE)
            }
            "name"->if(_registration.value.name == INITIAL_FIELD_STATE){
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(nameIconVisibility = View.INVISIBLE)
            }else{
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(nameIconVisibility = View.VISIBLE)
            }
            "email"->if(_registration.value.email == INITIAL_FIELD_STATE){
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(emailIconVisibility = View.INVISIBLE)
            }else{
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(emailIconVisibility = View.VISIBLE)
            }
            "password"->if(_registrationUI.value.passwordCurrentInputType ==
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(passwordCurrentInputType =
                    InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD,
                            passwordCurrentIcon = R.drawable.eye_on)
            }else{
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(passwordCurrentInputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                    passwordCurrentIcon = R.drawable.eye_off)
            }
            "passwordConfirm"->if(_registrationUI.value.passwordConfirmCurrentInputType ==
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(passwordConfirmCurrentInputType =
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD,
                            passwordConfirmCurrentIcon = R.drawable.eye_on)
            }else{
                _registrationUI.value =
                    _registrationUI
                        .value
                        .copy(passwordConfirmCurrentInputType =
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                            passwordConfirmCurrentIcon = R.drawable.eye_off)
            }
        }
    }

    fun createUserAccount(): UserRegisterUIModel{
       return UserRegisterUIModel(
            userName = _registration.value.userName,
            name = _registration.value.name,
            password = _registration.value.password,
            email = _registration.value.email,
            birthDate = dateConverter.convertToIso(_registration.value.birthDate),
            gender = _registration.value.gender
        )
    }

    fun isValid(): ButtonState {
       val buttonState = validateLoginUseCase(_registration.value.userName).status &&
        validateEmailUseCase(_registration.value.email).status &&
        validateNameField(_registration.value.name).status &&
        validateBirthDateUseCase(_registration.value.birthDate).status &&
        validatePasswordUseCase(_registration.value.password).status &&
        validatePasswordConfirmUseCase(_registration.value.passwordConfirm,
            _registration.value.password).status

        val buttonStyle  = if (buttonState) R.drawable.primary_button_shape else
            R.drawable.secondary_button_shape

        val buttonText =  if (buttonState) R.style.buttonText else
            R.style.buttonTextNotActive

        return ButtonState(buttonState,buttonStyle,buttonText)
    }

    fun setLogin(login:String){
        val validationResult = validateLoginUseCase(login)
        _registration.value = _registration.value.copy(userName = login,
            loginError = validationResult.errorMessage,
            loginErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setName(name:String){
        val validationResult = validateNameField(name)
        _registration.value = _registration.value.copy(name = name,
            nameError = validationResult.errorMessage,
            nameErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setEmail(email:String){
        val validationResult = validateEmailUseCase(email)
        _registration.value = _registration.value.copy(email = email,
            emailError = validationResult.errorMessage,
            emailErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setPassword(password:String){
        val validationResult  = validatePasswordUseCase(password)
        _registration.value = _registration.value.copy(password = password,
            passwordError = validationResult.errorMessage,
            passwordErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setPasswordConfirm(passwordConfirm: String,password: String){
        val validationResult = validatePasswordConfirmUseCase(passwordConfirm,password)
        _registration.value = _registration.value.copy(passwordConfirm = passwordConfirm,
            passwordConfirmError = validationResult.errorMessage,
            passwordConfirmErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setBirthdate(birthDate:Calendar){
        val  validationResult = validateBirthDateUseCase(birthDate.toString())
        _registration.value = _registration.value.copy(
            birthDate = dateConverter.convertSelectedToUI(birthDate),
            birthDateError = validationResult.errorMessage,
            birthDayErrorView = validationResult.errorState)
        _registrationValid.value = isValid()
    }

    fun setGender(value:Int){
        _registration.value = _registration.value.copy(gender = value)
    }

    private fun changeErrorMessageVisibility(message:String){
        _registration.value =
            _registration.value.copy(exceptionError = message)
        _registrationUI.value =
            _registrationUI.value.copy(registrationError = View.VISIBLE)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when(exception){
            is HttpException->{
                when(exception.code()){
                    400-> changeErrorMessageVisibility(UNIQUE_LOGIN_ERROR)
                }
            }
            else-> changeErrorMessageVisibility(EXCEPTION_ERROR)
        }
    }

    private fun successfulRegister(context: Context){
        val intent = Intent(context, AppNavigationActivity::class.java)
        startActivity(context,intent,null)
    }

    fun registerUser(context: Context) = viewModelScope.launch(exceptionHandler) {
        val registrationBody = createUserAccount()
        registerUseCase(registrationBody)
        _registrationUI.value =
        _registrationUI.value.copy(registrationError = View.GONE)
        successfulRegister(context)
    }
}