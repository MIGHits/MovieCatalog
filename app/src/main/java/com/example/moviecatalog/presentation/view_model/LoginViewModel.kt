package com.example.moviecatalog.presentation.view_model

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.EXCEPTION_ERROR
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.LOGIN_EXCEPTION
import com.example.moviecatalog.domain.entity.LoginBody
import com.example.moviecatalog.domain.usecase.LoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidateLoginUseCase
import com.example.moviecatalog.domain.usecase.Validation.ValidatePasswordUseCase
import com.example.moviecatalog.presentation.state.ButtonState
import com.example.moviecatalog.presentation.entity.LoginCredentials
import com.example.moviecatalog.presentation.state.LoginState
import com.example.moviecatalog.presentation.state.LoginUiState
import com.example.moviecatalog.presentation.view.AppNavigationActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val validateLoginUseCase: ValidateLoginUseCase,
                     private val validatePasswordUseCase: ValidatePasswordUseCase,
                     private val loginUseCase: LoginUseCase
):ViewModel(){


    private val _loginState = MutableStateFlow(LoginState.INITIAL)
    val loginState:StateFlow<LoginState>get() = _loginState

    private  val _login = MutableStateFlow(
        LoginCredentials(
            login = INITIAL_FIELD_STATE,
            loginError = null,
            password = INITIAL_FIELD_STATE,
            passwordError = null
        )
    )
    val login:StateFlow<LoginCredentials> get() = _login

    private val _loginUIState = MutableStateFlow(LoginUiState())
    val loginUIState:StateFlow<LoginUiState> get() = _loginUIState

    private val _loginValid = MutableStateFlow(ButtonState())
    val loginValid:StateFlow<ButtonState>get() = _loginValid

    fun changeUIState(element:String){
        when(element){
            "login"->if (login.value.login == INITIAL_FIELD_STATE){
                _loginUIState.value =
                    _loginUIState.value.copy(loginIconVisibility = View.INVISIBLE)
            }else{
                _loginUIState.value =
                    _loginUIState.value.copy(loginIconVisibility = View.VISIBLE)
            }

            "password"->if(_loginUIState.value.passwordCurrentInputType ==
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                _loginUIState.value = _loginUIState.value.copy(passwordCurrentInputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
                passwordCurrentIcon = R.drawable.eye_on)
            }else{
                _loginUIState.value = _loginUIState.value.copy(passwordCurrentInputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                passwordCurrentIcon = R.drawable.eye_off)
            }
        }
    }

    fun setLogin(login:String){
        val validationResult = validateLoginUseCase(login)
        _login.value = _login.value.copy(login = login,
            loginError = validationResult.errorMessage,
            loginErrorView = validationResult.errorState)
        _loginValid.value = isValid()
    }

    fun setPassword(password:String){
        val validationResult = validatePasswordUseCase(password)
        _login.value = _login.value.copy(password = password,
            passwordError = validationResult.errorMessage,
            passwordErrorView = validationResult.errorState)
        _loginValid.value = isValid()
    }

    fun isValid(): ButtonState {
        val buttonState = validateLoginUseCase(_login.value.login).status &&
                validatePasswordUseCase(_login.value.password).status

        val buttonStyle  = if (buttonState) R.drawable.primary_button_shape else
            R.drawable.secondary_button_shape

        val buttonText =  if (buttonState) R.style.buttonText else
            R.style.buttonTextNotActive

        return ButtonState(buttonState,buttonStyle,buttonText)
    }

    private fun createUserLogin():LoginBody{
        return LoginBody(
            username = _login.value.login,
            password = _login.value.password
        )
    }

    private fun changeErrorMessageVisibility(message:String){
        _login.value =
            _login.value.copy(exceptionError = message)
        _loginUIState.value =
            _loginUIState.value.copy(exceptionErrorView = View.VISIBLE)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when(exception){
            is HttpException ->{
                when(exception.code()){
                    400-> changeErrorMessageVisibility(LOGIN_EXCEPTION)
                }
            }
            else-> changeErrorMessageVisibility(EXCEPTION_ERROR)
        }
    }

    private fun successfulLogin(context: Context){
        val intent = Intent(context,AppNavigationActivity::class.java)
        startActivity(context,intent,null)
    }

    fun loginUser(context: Context) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            val user = createUserLogin()
            loginUseCase(user)
            _loginUIState.value =
                _loginUIState.value.copy(exceptionErrorView = View.GONE)
            successfulLogin(context)

    }
}